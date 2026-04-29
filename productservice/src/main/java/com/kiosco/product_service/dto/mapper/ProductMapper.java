package com.kiosco.product_service.dto.mapper;

import com.kiosco.product_service.domain.Product;
import com.kiosco.product_service.domain.ProductBranch;
import com.kiosco.product_service.domain.ProductVariant;
import com.kiosco.product_service.dto.response.ProductBranchStockResponse;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.dto.response.ProductVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "status", target = "status")
    ProductResponse toResponse(Product product);

    List<ProductVariantResponse> toVariantResponses(List<ProductVariant> variants);

    @Mapping(source = "productBranches", target = "branchStocks")
    @Mapping(target = "images", ignore = true)
    ProductVariantResponse toVariantResponse(ProductVariant variant);

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    List<ProductBranchStockResponse> toBranchStockResponses(List<ProductBranch> productBranches);

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "stock", target = "stock")
    ProductBranchStockResponse toBranchStockResponse(ProductBranch pb);
}