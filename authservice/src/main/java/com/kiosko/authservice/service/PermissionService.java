package com.kiosko.authservice.service;

import com.kiosko.authservice.contans.Messages;
import com.kiosko.authservice.domain.entity.Permission;
import com.kiosko.authservice.domain.repository.PermissionRepository;
import com.kiosko.authservice.dto.request.CreatePermissionRequest;
import com.kiosko.authservice.dto.request.UpdatePermissionRequest;
import com.kiosko.authservice.dto.response.PageResponse;
import com.kiosko.authservice.dto.response.PermissionDetailResponse;
import com.kiosko.authservice.dto.response.PermissionListResponse;
import com.kiosko.authservice.exception.PermissionAlreadyExistsException;
import com.kiosko.authservice.exception.PermissionNotFoundException;
import com.kiosko.authservice.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Transactional(readOnly = true)
    public PageResponse<PermissionListResponse> getAllPermissions(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<Permission> permissionPage = permissionRepository.findAll(pageable);

        List<PermissionListResponse> content = permissionPage.getContent().stream()
                .map(permissionMapper::toPermissionListResponse)
                .collect(Collectors.toList());

        return PageResponse.<PermissionListResponse>builder()
                .content(content)
                .page(permissionPage.getNumber())
                .size(permissionPage.getSize())
                .totalElements(permissionPage.getTotalElements())
                .totalPages(permissionPage.getTotalPages())
                .last(permissionPage.isLast())
                .first(permissionPage.isFirst())
                .build();
    }

    @Transactional(readOnly = true)
    public PermissionDetailResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(
                        String.format(Messages.Permission.NOT_FOUND_BY_ID, id)));

        return permissionMapper.toPermissionDetailResponse(permission);
    }

    @Transactional
    public PermissionDetailResponse createPermission(CreatePermissionRequest request) {
        log.info(String.format(Messages.Permission.CREATE_ATTEMPT, request.getResource(), request.getAction()));

        if (permissionRepository.existsByResourceAndAction(request.getResource(), request.getAction())) {
            throw new PermissionAlreadyExistsException(
                    String.format(Messages.Permission.ALREADY_EXISTS, request.getResource(), request.getAction()));
        }

        Permission permission = Permission.builder()
                .resource(request.getResource())
                .action(request.getAction())
                .description(request.getDescription())
                .build();

        permission = permissionRepository.save(permission);

        log.info(String.format(Messages.Permission.CREATE_SUCCESS, permission.getFullPermission()));

        return permissionMapper.toPermissionDetailResponse(permission);
    }

    @Transactional
    public PermissionDetailResponse updatePermission(Long id, UpdatePermissionRequest request) {
        log.info(String.format(Messages.Permission.UPDATE_ATTEMPT, id));

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(
                        String.format(Messages.Permission.NOT_FOUND_BY_ID, id)));

        if (request.getResource() != null || request.getAction() != null) {
            String newResource = request.getResource() != null ? request.getResource() : permission.getResource();
            String newAction = request.getAction() != null ? request.getAction() : permission.getAction();

            if (!newResource.equals(permission.getResource()) || !newAction.equals(permission.getAction())) {
                if (permissionRepository.existsByResourceAndAction(newResource, newAction)) {
                    throw new PermissionAlreadyExistsException(
                            String.format(Messages.Permission.ALREADY_EXISTS, newResource, newAction));
                }
            }
        }

        if (request.getResource() != null) {
            permission.setResource(request.getResource());
        }

        if (request.getAction() != null) {
            permission.setAction(request.getAction());
        }

        if (request.getDescription() != null) {
            permission.setDescription(request.getDescription());
        }

        permission = permissionRepository.save(permission);

        log.info(String.format(Messages.Permission.UPDATE_SUCCESS, permission.getFullPermission()));

        return permissionMapper.toPermissionDetailResponse(permission);
    }

    @Transactional
    public void deletePermission(Long id) {
        log.info(String.format(Messages.Permission.DELETE_ATTEMPT, id));

        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(
                        String.format(Messages.Permission.NOT_FOUND_BY_ID, id)));

        if (permission.getRoles() != null && !permission.getRoles().isEmpty()) {
            throw new IllegalStateException(Messages.Permission.DELETE_CONFLICT);
        }

        permissionRepository.delete(permission);

        log.info(String.format(Messages.Permission.DELETE_SUCCESS, permission.getFullPermission()));
    }

    @Transactional(readOnly = true)
    public List<PermissionListResponse> getPermissionsByResource(String resource) {
        List<Permission> permissions = permissionRepository.findByResource(resource);

        return permissions.stream()
                .map(permissionMapper::toPermissionListResponse)
                .collect(Collectors.toList());
    }
}
