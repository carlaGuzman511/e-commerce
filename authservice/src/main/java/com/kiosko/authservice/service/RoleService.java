package com.kiosko.authservice.service;

import com.kiosko.authservice.contans.Messages;
import com.kiosko.authservice.domain.entity.Permission;
import com.kiosko.authservice.domain.entity.Role;
import com.kiosko.authservice.domain.repository.PermissionRepository;
import com.kiosko.authservice.domain.repository.RoleRepository;
import com.kiosko.authservice.dto.request.AssignPermissionsToRoleRequest;
import com.kiosko.authservice.dto.request.CreateRoleRequest;
import com.kiosko.authservice.dto.request.UpdateRoleRequest;
import com.kiosko.authservice.dto.response.PageResponse;
import com.kiosko.authservice.dto.response.RoleDetailResponse;
import com.kiosko.authservice.dto.response.RoleListResponse;
import com.kiosko.authservice.exception.RoleAlreadyExistsException;
import com.kiosko.authservice.exception.RoleNotFoundException;
import com.kiosko.authservice.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public PageResponse<RoleListResponse> getAllRoles(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<Role> rolePage = roleRepository.findAll(pageable);

        List<RoleListResponse> content = rolePage.getContent().stream()
                .map(roleMapper::toRoleListResponse)
                .collect(Collectors.toList());

        return PageResponse.<RoleListResponse>builder()
                .content(content)
                .page(rolePage.getNumber())
                .size(rolePage.getSize())
                .totalElements(rolePage.getTotalElements())
                .totalPages(rolePage.getTotalPages())
                .last(rolePage.isLast())
                .first(rolePage.isFirst())
                .build();
    }

    @Transactional(readOnly = true)
    public RoleDetailResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(String.format(Messages.Role.NOT_FOUND_BY_ID, id)));

        return roleMapper.toRoleDetailResponse(role);
    }

    @Transactional
    public RoleDetailResponse createRole(CreateRoleRequest request) {
        log.info(String.format(Messages.Role.CREATE_ATTEMPT, request.getName()));

        if (roleRepository.existsByName(request.getName())) {
            throw new RoleAlreadyExistsException(String.format(Messages.Role.ALREADY_EXISTS, request.getName()));
        }

        Set<Permission> permissions = new HashSet<>();
        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
            permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissionIds()));
        }

        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .tenantId(request.getTenantId())
                .permissions(permissions)
                .build();

        role = roleRepository.save(role);

        log.info(String.format(Messages.Role.CREATE_SUCCESS, role.getName()));

        return roleMapper.toRoleDetailResponse(role);
    }

    @Transactional
    public RoleDetailResponse updateRole(Long id, UpdateRoleRequest request) {
        log.info(String.format(Messages.Role.UPDATE_ATTEMPT, id));

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(String.format(Messages.Role.NOT_FOUND_BY_ID, id)));

        if (request.getName() != null && !request.getName().equals(role.getName())) {
            if (roleRepository.existsByName(request.getName())) {
                throw new RoleAlreadyExistsException(String.format(Messages.Role.ALREADY_EXISTS, request.getName()));
            }
            role.setName(request.getName());
        }

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        if (request.getTenantId() != null) {
            role.setTenantId(request.getTenantId());
        }

        role = roleRepository.save(role);

        log.info(String.format(Messages.Role.UPDATE_SUCCESS, role.getName()));

        return roleMapper.toRoleDetailResponse(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        log.info(String.format(Messages.Role.DELETE_ATTEMPT, id));

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(String.format(Messages.Role.NOT_FOUND_BY_ID, id)));

        if (role.getUsers() != null && !role.getUsers().isEmpty()) {
            throw new IllegalStateException(Messages.Role.DELETE_CONFLICT);
        }

        roleRepository.delete(role);

        log.info(String.format(Messages.Role.DELETE_SUCCESS, role.getName()));
    }

    @Transactional
    public RoleDetailResponse assignPermissions(Long roleId, AssignPermissionsToRoleRequest request) {
        log.info(String.format(Messages.Role.ASSIGN_ATTEMPT, roleId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(String.format(Messages.Role.NOT_FOUND_BY_ID, roleId)));

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissionIds()));
        role.setPermissions(permissions);

        role = roleRepository.save(role);

        log.info(String.format(Messages.Role.ASSIGN_SUCCESS, role.getName()));

        return roleMapper.toRoleDetailResponse(role);
    }

    @Transactional
    public RoleDetailResponse removePermission(Long roleId, Long permissionId) {
        log.info(String.format(Messages.Role.REMOVE_ATTEMPT, permissionId, roleId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(String.format(Messages.Role.NOT_FOUND_BY_ID, roleId)));

        role.getPermissions().removeIf(permission -> permission.getId().equals(permissionId));
        role = roleRepository.save(role);

        log.info(String.format(Messages.Role.REMOVE_SUCCESS, role.getName()));

        return roleMapper.toRoleDetailResponse(role);
    }

    @Transactional(readOnly = true)
    public RoleDetailResponse getRoleWithUsers(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(String.format(Messages.Role.NOT_FOUND_BY_ID, id)));

        return roleMapper.toRoleDetailResponse(role);
    }
}
