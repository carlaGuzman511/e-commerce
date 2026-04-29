package com.kiosco.product_service.command.category;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.spec.SafeAbstractCommand;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.dto.request.CategoryCreateRequest;
import com.kiosco.product_service.dto.response.CategoryResponse;
import com.kiosco.product_service.exeption.CategoryException;
import com.kiosco.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCategoryCmd extends SafeAbstractCommand<CategoryCreateRequest, CategoryResponse> {
    private final CategoryService categoryService;

    @Override
    protected void preExecute(CategoryCreateRequest request) {
        String tenantId = TenantContext.get();
        if (categoryService.existsByNameAndTenant(tenantId, request.getName())) {
            throw new CategoryException(Messages.CATEGORY_ALREADY_EXISTS);
        }
    }

    @Override
    protected CategoryResponse safeExecute(CategoryCreateRequest request) {
        return categoryService.create(request);
    }

    @Override
    protected void postExecute(CategoryResponse response) {
        log.info("Product created with ID: {}", response.getId());
    }
}
