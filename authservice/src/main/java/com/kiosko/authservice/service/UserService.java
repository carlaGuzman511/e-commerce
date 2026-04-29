package com.kiosko.authservice.service;

import com.kiosko.authservice.contans.Messages;
import com.kiosko.authservice.domain.entity.Permission;
import com.kiosko.authservice.domain.entity.Role;
import com.kiosko.authservice.domain.entity.User;
import com.kiosko.authservice.domain.repository.PermissionRepository;
import com.kiosko.authservice.domain.repository.RoleRepository;
import com.kiosko.authservice.domain.repository.UserRepository;
import com.kiosko.authservice.dto.request.AssignPermissionRequest;
import com.kiosko.authservice.dto.request.AssignRoleRequest;
import com.kiosko.authservice.dto.request.CreateUserRequest;
import com.kiosko.authservice.dto.request.UpdateUserRequest;
import com.kiosko.authservice.dto.response.PageResponse;
import com.kiosko.authservice.dto.response.UserDetailResponse;
import com.kiosko.authservice.dto.response.UserListResponse;
import com.kiosko.authservice.exception.UserAlreadyExistsException;
import com.kiosko.authservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public PageResponse<UserListResponse> getAllUsers(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<User> userPage = userRepository.findAll(pageable);

        List<UserListResponse> content = userPage.getContent().stream()
                .map(this::toUserListResponse)
                .collect(Collectors.toList());

        return PageResponse.<UserListResponse>builder()
                .content(content)
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .first(userPage.isFirst())
                .build();
    }

    @Transactional(readOnly = true)
    public UserDetailResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(Messages.User.NOT_FOUND_BY_ID, id)));

        return toUserDetailResponse(user);
    }

    @Transactional
    public UserDetailResponse createUser(CreateUserRequest request) {
        log.info(String.format(Messages.User.CREATE_ATTEMPT, request.getEmail()));

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(Messages.User.EMAIL_ALREADY_USED);
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(Messages.User.USERNAME_ALREADY_USED);
        }

        Set<Role> roles = request.getRoleIds() != null ? new HashSet<>(roleRepository.findAllById(request.getRoleIds())) : new HashSet<>();
        Set<Permission> permissions = request.getPermissionIds() != null ? new HashSet<>(permissionRepository.findAllById(request.getPermissionIds())) : new HashSet<>();

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .tenantId(request.getTenantId())
                .active(request.getActive() != null ? request.getActive() : true)
                .emailVerified(request.getEmailVerified() != null ? request.getEmailVerified() : false)
                .roles(roles)
                .permissions(permissions)
                .build();

        user = userRepository.save(user);

        log.info(String.format(Messages.User.CREATE_SUCCESS, user.getEmail()));

        return toUserDetailResponse(user);
    }

    @Transactional
    public UserDetailResponse updateUser(Long id, UpdateUserRequest request) {
        log.info(String.format(Messages.User.UPDATE_ATTEMPT, id));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(Messages.User.NOT_FOUND_BY_ID, id)));

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new UserAlreadyExistsException(Messages.User.EMAIL_ALREADY_USED);
            }
            user.setEmail(request.getEmail());
        }

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new UserAlreadyExistsException(Messages.User.USERNAME_ALREADY_USED);
            }
            user.setUsername(request.getUsername());
        }

        if (request.getTenantId() != null) user.setTenantId(request.getTenantId());
        if (request.getActive() != null) user.setActive(request.getActive());
        if (request.getEmailVerified() != null) user.setEmailVerified(request.getEmailVerified());

        user = userRepository.save(user);

        log.info(String.format(Messages.User.UPDATE_SUCCESS, user.getEmail()));

        return toUserDetailResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info(String.format(Messages.User.DELETE_ATTEMPT, id));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(Messages.User.NOT_FOUND_BY_ID, id)));

        user.setActive(false);
        userRepository.save(user);

        log.info(String.format(Messages.User.DELETE_SUCCESS, user.getEmail()));
    }

    @Transactional
    public UserDetailResponse toggleUserStatus(Long id, boolean active) {
        log.info(String.format(Messages.User.STATUS_TOGGLE_ATTEMPT, id, active));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(Messages.User.NOT_FOUND_BY_ID, id)));

        user.setActive(active);
        if (active) user.resetFailedAttempts();

        user = userRepository.save(user);

        log.info(String.format(Messages.User.STATUS_TOGGLE_SUCCESS, active, user.getEmail()));

        return toUserDetailResponse(user);
    }

    @Transactional
    public UserDetailResponse assignRoles(Long userId, AssignRoleRequest request) {
        log.info(String.format(Messages.User.ASSIGN_ROLE_ATTEMPT, userId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(Messages.User.NOT_FOUND_BY_ID, userId)));

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoleIds()));
        user.setRoles(roles);

        user = userRepository.save(user);

        log.info(String.format(Messages.User.ASSIGN_ROLE_SUCCESS, user.getEmail()));

        return toUserDetailResponse(user);
    }

    @Transactional
    public UserDetailResponse removeRole(Long userId, Long roleId) {
        log.info(String.format(Messages.User.REMOVE_ROLE_ATTEMPT, roleId, userId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(Messages.User.NOT_FOUND_BY_ID, userId)));

        user.getRoles().removeIf(role -> role.getId().equals(roleId));
        user = userRepository.save(user);

        log.info(String.format(Messages.User.REMOVE_ROLE_SUCCESS, user.getEmail()));

        return toUserDetailResponse(user);
    }

    @Transactional
    public UserDetailResponse assignPermissions(Long userId, AssignPermissionRequest request) {
        log.info(String.format(Messages.User.ASSIGN_PERMISSION_ATTEMPT, userId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(Messages.User.NOT_FOUND_BY_ID, userId)));

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissionIds()));
        user.setPermissions(permissions);

        user = userRepository.save(user);

        log.info(String.format(Messages.User.ASSIGN_PERMISSION_SUCCESS, user.getEmail()));

        return toUserDetailResponse(user);
    }

    @Transactional
    public UserDetailResponse removePermission(Long userId, Long permissionId) {
        log.info(String.format(Messages.User.REMOVE_PERMISSION_ATTEMPT, permissionId, userId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format(Messages.User.NOT_FOUND_BY_ID, userId)));

        user.getPermissions().removeIf(permission -> permission.getId().equals(permissionId));
        user = userRepository.save(user);

        log.info(String.format(Messages.User.REMOVE_PERMISSION_SUCCESS, user.getEmail()));

        return toUserDetailResponse(user);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserListResponse> searchUsers(String query, int page, int size) {
        log.info(String.format(Messages.User.SEARCH_ATTEMPT, query));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<User> userPage = userRepository.findByEmailContainingIgnoreCaseOrUsernameContainingIgnoreCase(query, query, pageable);

        List<UserListResponse> content = userPage.getContent().stream()
                .map(this::toUserListResponse)
                .collect(Collectors.toList());

        return PageResponse.<UserListResponse>builder()
                .content(content)
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .first(userPage.isFirst())
                .build();
    }

    private UserListResponse toUserListResponse(User user) {
        return UserListResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .tenantId(user.getTenantId())
                .username(user.getUsername())
                .active(user.getActive())
                .emailVerified(user.getEmailVerified())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    private UserDetailResponse toUserDetailResponse(User user) {
        return UserDetailResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .image(user.getImage())
                .active(user.getActive())
                .emailVerified(user.getEmailVerified())
                .tenantId(user.getTenantId())
                .authProvider(user.getAuthProvider().name())
                .failedLoginAttempts(user.getFailedLoginAttempts())
                .lastLoginAt(user.getLastLoginAt())
                .roles(user.getRoles().stream()
                        .map(role -> UserDetailResponse.RoleInfo.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .description(role.getDescription())
                                .build())
                        .collect(Collectors.toSet()))
                .directPermissions(user.getPermissions().stream()
                        .map(permission -> UserDetailResponse.PermissionInfo.builder()
                                .id(permission.getId())
                                .resource(permission.getResource())
                                .action(permission.getAction())
                                .description(permission.getDescription())
                                .build())
                        .collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

