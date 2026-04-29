package com.kiosko.authservice.service;

import com.kiosko.authservice.contans.Messages;
import com.kiosko.authservice.dto.response.SimpleAuthResponse;
import com.kiosko.authservice.dto.response.SimpleUserResponse;
import com.kiosko.authservice.domain.entity.RefreshToken;
import com.kiosko.authservice.domain.entity.Role;
import com.kiosko.authservice.domain.entity.User;
import com.kiosko.authservice.domain.repository.RefreshTokenRepository;
import com.kiosko.authservice.domain.repository.RoleRepository;
import com.kiosko.authservice.domain.repository.UserRepository;
import com.kiosko.authservice.dto.request.LoginRequest;
import com.kiosko.authservice.dto.request.RefreshTokenRequest;
import com.kiosko.authservice.dto.request.RegisterRequest;
import com.kiosko.authservice.exception.*;
import com.kiosko.authservice.mapper.UserMapper;
import com.kiosko.authservice.security.CustomUserDetails;
import com.kiosko.authservice.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Value("${security.login.max-failed-attempts}")
    private Integer maxFailedAttempts;

    @Transactional
    public SimpleAuthResponse login(LoginRequest request) {
        log.info(String.format(Messages.Auth.LOGIN_ATTEMPT, request.getEmail()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException(Messages.Auth.INVALID_CREDENTIALS));

        if (!user.getActive()) {
            throw new AccountLockedException(Messages.Auth.ACCOUNT_INACTIVE);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            handleFailedLogin(user);
            throw new InvalidCredentialsException(Messages.Auth.INVALID_CREDENTIALS);
        }

        user.resetFailedAttempts();
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessTokenStr = jwtUtils.generateAccessToken(userDetails);
        String refreshTokenStr = jwtUtils.generateRefreshToken(userDetails);

        saveRefreshToken(user, refreshTokenStr);

        SimpleUserResponse userResponse = userMapper.toSimpleUserResponse(user);

        log.info(String.format(Messages.Auth.LOGIN_SUCCESS, user.getEmail()));

        return SimpleAuthResponse.builder()
                .accessToken(accessTokenStr)
                .refreshToken(refreshTokenStr)
                .expiresIn(accessTokenExpiration)
                .user(userResponse)
                .build();
    }

    @Transactional
    public SimpleAuthResponse register(RegisterRequest request) {
        log.info(String.format(Messages.Auth.REGISTRATION_ATTEMPT, request.getEmail()));

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(Messages.Auth.EMAIL_ALREADY_REGISTERED);
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(Messages.Auth.USERNAME_ALREADY_TAKEN);
        }

        Role clienteRole = roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new RuntimeException(Messages.Auth.CLIENT_ROLE_NOT_FOUND));

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .emailVerified(false)
                .roles(Set.of(clienteRole))
                .build();

        user = userRepository.save(user);

        log.info(String.format(Messages.Auth.REGISTRATION_SUCCESS, user.getEmail()));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessTokenStr = jwtUtils.generateAccessToken(userDetails);
        String refreshTokenStr = jwtUtils.generateRefreshToken(userDetails);

        saveRefreshToken(user, refreshTokenStr);

        SimpleUserResponse userResponse = userMapper.toSimpleUserResponse(user);

        return SimpleAuthResponse.builder()
                .accessToken(accessTokenStr)
                .refreshToken(refreshTokenStr)
                .expiresIn(accessTokenExpiration)
                .user(userResponse)
                .build();
    }

    @Transactional
    public SimpleAuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshTokenStr = request.getRefreshToken();

        if (!jwtUtils.validateToken(refreshTokenStr)) {
            throw new AuthException(Messages.Auth.INVALID_REFRESH_TOKEN);
        }

        String email = jwtUtils.extractUsername(refreshTokenStr);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Messages.Auth.USER_NOT_FOUND));

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(() -> new AuthException(Messages.Auth.REFRESH_TOKEN_NOT_FOUND));

        if (!refreshToken.isValid()) {
            throw new AuthException(Messages.Auth.REFRESH_TOKEN_INVALID);
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String newAccessTokenStr = jwtUtils.generateAccessToken(userDetails);
        String newRefreshTokenStr = jwtUtils.generateRefreshToken(userDetails);

        refreshToken.revoke();
        refreshTokenRepository.save(refreshToken);

        saveRefreshToken(user, newRefreshTokenStr);

        SimpleUserResponse userResponse = userMapper.toSimpleUserResponse(user);

        log.info(String.format(Messages.Auth.TOKEN_REFRESHED, user.getEmail()));

        return SimpleAuthResponse.builder()
                .accessToken(newAccessTokenStr)
                .refreshToken(newRefreshTokenStr)
                .expiresIn(accessTokenExpiration)
                .user(userResponse)
                .build();
    }

    @Transactional
    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(token -> {
                    token.revoke();
                    refreshTokenRepository.save(token);
                    log.info(Messages.Auth.LOGOUT_SUCCESS);
                });
    }

    private void handleFailedLogin(User user) {
        user.incrementFailedAttempts();

        if (user.getFailedLoginAttempts() >= maxFailedAttempts) {
            user.lock();
            log.warn(String.format(Messages.Auth.ACCOUNT_LOCKED,
                    user.getEmail(), user.getFailedLoginAttempts()));
        }

        userRepository.save(user);
    }

    private void saveRefreshToken(User user, String token) {
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiresAt(expiresAt)
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);
    }
}
