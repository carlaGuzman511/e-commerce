package com.kiosco.product_service.controller;

import com.kiosco.product_service.context.TenantContext;
import com.kiosco.product_service.command.product.*;
import com.kiosco.product_service.command.spec.SafeCommandExecutor;
import com.kiosco.product_service.constants.ApiPaths;
import com.kiosco.product_service.constants.Messages;
import com.kiosco.product_service.dto.ApiResponse;
import com.kiosco.product_service.dto.request.ProductCreateRequest;
import com.kiosco.product_service.dto.request.ProductUpdateRequest;
import com.kiosco.product_service.dto.request.StockCheckRequest;
import com.kiosco.product_service.dto.request.StockUpdateRequest;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.dto.response.StockResponse;
import com.kiosco.product_service.service.ProductQueryService;
import com.kiosco.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.Product.BASE)
@RequiredArgsConstructor
public class ProductController {

    private final SafeCommandExecutor commandExecutor;
    private final ProductService productService;
    private final ProductQueryService productQueryService;

    @GetMapping(ApiPaths.Product.BY_ID)
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("id") Long id) {
        GetProductByIdCmd cmd = new GetProductByIdCmd(productService);
        ProductResponse response = commandExecutor.execute(cmd, id);

        ApiResponse<ProductResponse> apiResponse = ApiResponse.<ProductResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.PRODUCT_RETRIEVED)
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        CreateProductCmd cmd = new CreateProductCmd(productService);
        ProductResponse response = commandExecutor.execute(cmd, request);

        ApiResponse<ProductResponse> apiResponse = ApiResponse.<ProductResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CREATED.value())
                .message(Messages.PRODUCT_CREATED)
                .data(response)
                .build();

        return ResponseEntity.created(URI.create(ApiPaths.Product.BASE + "/" + response.getId()))
                .body(apiResponse);
    }

    @PostMapping(ApiPaths.Product.BRANCH_STOCK_RESERVE)
    public ResponseEntity<ApiResponse<Void>> reserveStock(@RequestBody List<StockUpdateRequest> items) {
        ReserveStockCmd cmd = new ReserveStockCmd(productService);
        commandExecutor.execute(cmd, items);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.STOCK_RESERVED)
                .build());
    }

    @PostMapping(ApiPaths.Product.BRANCH_STOCK_RESTORE)
    public ResponseEntity<ApiResponse<Void>> restoreStock(@RequestBody List<StockUpdateRequest> items) {
        RestoreStockCmd cmd = new RestoreStockCmd(productService);
        commandExecutor.execute(cmd, items);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.STOCK_RESTORED)
                .build());
    }

    @PostMapping(ApiPaths.Product.BRANCH_STOCK_CHECK)
    public ResponseEntity<ApiResponse<StockResponse>> checkStock(@RequestBody StockCheckRequest request) {
        GetStockCmd cmd = new GetStockCmd(productService);
        StockResponse response = commandExecutor.execute(cmd, request);

        return ResponseEntity.ok(ApiResponse.<StockResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.STOCK_CHECK_COMPLETED)
                .data(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> listProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "active", required = false) Boolean active) {

        Page<ProductResponse> pagedProducts = productQueryService.getAll(
                PageRequest.of(page, size),
                categoryId,
                sort,
                filter,
                active
        );

        ApiResponse<Page<ProductResponse>> response = ApiResponse.<Page<ProductResponse>>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.PRODUCTS_RETRIEVED)
                .data(pagedProducts)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @Valid @RequestBody ProductUpdateRequest request) {

        UpdateProductCmd cmd = new UpdateProductCmd(productService);
        ProductResponse response = commandExecutor.execute(cmd, request);

        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.PRODUCT_UPDATED)
                .data(response)
                .build());
    }

    @DeleteMapping(ApiPaths.Product.BY_ID)
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("id") Long id) {
        DeleteProductCmd cmd = new DeleteProductCmd(productService);
        commandExecutor.execute(cmd, id);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.PRODUCT_DELETED)
                .build());
    }

    @GetMapping(ApiPaths.Product.SEARCH)
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String tenantId = TenantContext.get();

        Page<ProductResponse> results = productQueryService.searchProducts(
                tenantId,
                PageRequest.of(page, size),
                name,
                categoryId,
                minPrice,
                maxPrice
        );

        ApiResponse<Page<ProductResponse>> response = ApiResponse.<Page<ProductResponse>>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.PRODUCT_SEARCH_COMPLETED)
                .data(results)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(ApiPaths.Product.ACTIVE_WITH_STOCK)
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> listActiveProductsWithStock(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String tenantId = TenantContext.get();
        Page<ProductResponse> products = productQueryService.findAllWithStockByTenant(tenantId, PageRequest.of(page, size));

        ApiResponse<Page<ProductResponse>> response = ApiResponse.<Page<ProductResponse>>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.PRODUCTS_WITH_STOCK_RETRIEVED)
                .data(products)
                .build();

        return ResponseEntity.ok(response);
    }
}