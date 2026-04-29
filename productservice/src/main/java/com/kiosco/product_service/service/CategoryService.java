package com.kiosco.product_service.service;

import com.kiosco.product_service.domain.Category;
import com.kiosco.product_service.dto.request.CategoryCreateRequest;
import com.kiosco.product_service.dto.request.CategoryUpdateRequest;
import com.kiosco.product_service.dto.response.CategoryResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    boolean existsByNameAndTenant(String tenantIdHeader, String name);

    CategoryResponse create(CategoryCreateRequest request);

    CategoryResponse update(CategoryUpdateRequest request, Category category);

    CategoryResponse getById(Long id);

    Category findByIdAndTenantId(Long id, String tenantId);

    Optional<Category> findByTenantIdAndNameIgnoreCase(String tenantId, String name);
    List<CategoryResponse> findAllActiveByTenant(String tenantId);
}
