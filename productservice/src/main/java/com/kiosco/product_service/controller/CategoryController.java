package com.kiosco.product_service.controller;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.category.CreateCategoryCmd;
import com.kiosco.product_service.command.category.UpdateCategoryCmd;
import com.kiosco.product_service.command.spec.SafeCommandExecutor;
import com.kiosco.product_service.constants.ApiPaths;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.dto.ApiResponse;
import com.kiosco.product_service.dto.request.CategoryCreateRequest;
import com.kiosco.product_service.dto.request.CategoryUpdateRequest;
import com.kiosco.product_service.dto.response.CategoryResponse;
import com.kiosco.product_service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.Category.BASE)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final SafeCommandExecutor commandExecutor;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(
            @Valid @RequestBody CategoryCreateRequest request) throws BadRequestException {
        CreateCategoryCmd cmd = new CreateCategoryCmd(categoryService);
        CategoryResponse response = commandExecutor.execute(cmd, request);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CREATED.value())
                .message(Messages.CATEGORY_CREATED)
                .data(response)
                .build();

        return ResponseEntity.created(URI.create(ApiPaths.Category.BASE + "/" + response.getId()))
                .body(apiResponse);
    }

    @PutMapping(ApiPaths.Category.BY_ID)
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CategoryUpdateRequest request) {
        request.setId(id);
        UpdateCategoryCmd cmd = new UpdateCategoryCmd(categoryService);
        CategoryResponse response = commandExecutor.execute(cmd, request);

        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.CATEGORY_UPDATED)
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(ApiPaths.Category.BY_ID)
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable("id") Long id) {
        CategoryResponse response = categoryService.getById(id);

        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.CATEGORY_RETRIEVED)
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> listCategories() {
        String tenantId = TenantContext.get();
        List<CategoryResponse> categories = categoryService.findAllActiveByTenant(tenantId);

        ApiResponse<List<CategoryResponse>> response = ApiResponse.<List<CategoryResponse>>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message("Available categories retrieved successfully")
                .data(categories)
                .build();

        return ResponseEntity.ok(response);
    }
}
