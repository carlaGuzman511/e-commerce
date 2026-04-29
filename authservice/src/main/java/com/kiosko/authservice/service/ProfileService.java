package com.kiosko.authservice.service;

import com.kiosko.authservice.contans.Messages;
import com.kiosko.authservice.domain.entity.User;
import com.kiosko.authservice.domain.repository.UserRepository;
import com.kiosko.authservice.dto.request.ChangePasswordRequest;
import com.kiosko.authservice.dto.request.UpdateProfileRequest;
import com.kiosko.authservice.dto.response.SimpleUserResponse;
import com.kiosko.authservice.exception.AuthException;
import com.kiosko.authservice.exception.UserAlreadyExistsException;
import com.kiosko.authservice.exception.UserNotFoundException;
import com.kiosko.authservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public SimpleUserResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Messages.User.NOT_FOUND));

        return userMapper.toSimpleUserResponse(user);
    }

    @Transactional
    public SimpleUserResponse updateProfile(String email, UpdateProfileRequest request) {
        log.info(String.format(Messages.Profile.UPDATE_ATTEMPT, email));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Messages.User.NOT_FOUND));

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new UserAlreadyExistsException(Messages.User.USERNAME_ALREADY_USED_TAKEN);
            }
            user.setUsername(request.getUsername());
        }

        if (request.getImage() != null) {
            user.setImage(request.getImage());
        }

        user = userRepository.save(user);

        log.info(String.format(Messages.Profile.UPDATE_SUCCESS, email));

        return userMapper.toSimpleUserResponse(user);
    }

    @Transactional
    public void changePassword(String email, ChangePasswordRequest request) {
        log.info(String.format(Messages.Profile.PASSWORD_CHANGE_ATTEMPT, email));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(Messages.User.NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new AuthException(Messages.Profile.INVALID_CURRENT_PASSWORD);
        }

        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new AuthException(Messages.Profile.PASSWORDS_MUST_DIFFER);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info(String.format(Messages.Profile.PASSWORD_CHANGE_SUCCESS, email));
    }
}
