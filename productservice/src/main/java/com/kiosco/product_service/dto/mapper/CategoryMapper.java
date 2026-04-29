package com.kiosco.product_service.dto.mapper;

import com.kiosco.product_service.domain.Category;
import com.kiosco.product_service.dto.request.CategoryCreateRequest;
import com.kiosco.product_service.dto.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toEntity(CategoryCreateRequest req);

    CategoryResponse toResponse(Category category);
}
