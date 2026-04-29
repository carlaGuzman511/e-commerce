package com.kiosco.product_service.service;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.domain.Category;
import com.kiosco.product_service.dto.mapper.CategoryMapper;
import com.kiosco.product_service.dto.request.CategoryCreateRequest;
import com.kiosco.product_service.dto.request.CategoryUpdateRequest;
import com.kiosco.product_service.dto.response.CategoryResponse;
import com.kiosco.product_service.exeption.CategoryException;
import com.kiosco.product_service.exeption.NotFoundException;
import com.kiosco.product_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public boolean existsByNameAndTenant(String tenantIdHeader, String name) {
        String tenantId = TenantContext.get();

        if (categoryRepository.existsByTenantIdAndNameIgnoreCaseAndActiveTrue(tenantId, name)) {
            throw new CategoryException(Messages.CATEGORY_ALREADY_EXISTS);
        }
        return false;
    }

    @Override
    public Category findByIdAndTenantId(Long id, String tenantId) {
        return categoryRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new CategoryException(Messages.CATEGORY_NOT_FOUND));
    }

    @Override
    public Optional<Category> findByTenantIdAndNameIgnoreCase(String tenantId, String name) {
        return categoryRepository.findByTenantIdAndNameIgnoreCase(tenantId, name);
    }

    @Transactional
    public CategoryResponse create(CategoryCreateRequest request) {
        String tenantId = TenantContext.get();
        Category category;
        Optional<Category> existingInactive = categoryRepository.findByTenantIdAndNameIgnoreCaseAndActiveFalse(tenantId, request.getName());
        if (existingInactive.isPresent()) {
            category = existingInactive.get();
            category.setActive(true);
            category.setDescription(request.getDescription());
            category.setOrderIndex(request.getOrderIndex());
            categoryRepository.save(category);
        } else {
            category = categoryMapper.toEntity(request);
            category.setTenantId(tenantId);
        }
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Transactional
    public CategoryResponse update(CategoryUpdateRequest request, Category category) {
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        Category updated = categoryRepository.save(category);
        return categoryMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        String tenantId = TenantContext.get();
        Category category = categoryRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new NotFoundException(Messages.CATEGORY_NOT_FOUND));

        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> findAllActiveByTenant(String tenantId) {
        List<Category> list = categoryRepository.findAllByTenantIdAndActiveTrueOrderByNameAsc(tenantId);
        return list.stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
