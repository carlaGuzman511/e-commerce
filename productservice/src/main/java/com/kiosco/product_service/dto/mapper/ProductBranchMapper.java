package com.kiosco.product_service.dto.mapper;

import com.kiosco.product_service.domain.ProductBranch;
import com.kiosco.product_service.dto.response.ProductBranchStockResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductBranchMapper {

    @Mapping(source = "branch.id", target = "branchId")
    ProductBranchStockResponse toResponse(ProductBranch productBranch);

    List<ProductBranchStockResponse> toResponses(List<ProductBranch> productBranches);
}
