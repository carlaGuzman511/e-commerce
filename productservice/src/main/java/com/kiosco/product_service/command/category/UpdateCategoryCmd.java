package com.kiosco.product_service.command.category;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.spec.SafeAbstractCommand;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.domain.Category;
import com.kiosco.product_service.dto.request.CategoryUpdateRequest;
import com.kiosco.product_service.dto.response.CategoryResponse;
import com.kiosco.product_service.exeption.CategoryException;
import com.kiosco.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UpdateCategoryCmd extends SafeAbstractCommand<CategoryUpdateRequest, CategoryResponse> {
    private final CategoryService categoryService;
    private Category categoryUpdate;

    @Override
    protected void preExecute(CategoryUpdateRequest request) {
        String tenantId = TenantContext.get();
        categoryUpdate = categoryService.findByIdAndTenantId(request.getId(), tenantId);
        if (Boolean.FALSE.equals(categoryUpdate.getActive())) {
            throw new CategoryException(Messages.CATEGORY_INACTIVE);
        }
        if (!categoryUpdate.getName().equalsIgnoreCase(request.getName())) {
            Optional<Category> existing = categoryService.findByTenantIdAndNameIgnoreCase(tenantId, request.getName());

            if (existing.isPresent() && !existing.get().getId().equals(request.getId())) {
                throw new CategoryException(Messages.CATEGORY_ALREADY_EXISTS);
            }
        }
    }

    @Override
    protected CategoryResponse safeExecute(CategoryUpdateRequest request) {
        return categoryService.update(request, categoryUpdate);
    }

    @Override
    protected void postExecute(CategoryResponse response) {
        log.info("Product created with ID: {}", response.getId());
    }
}
