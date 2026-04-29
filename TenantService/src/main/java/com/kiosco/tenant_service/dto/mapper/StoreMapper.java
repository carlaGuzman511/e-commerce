package com.kiosco.tenant_service.dto.mapper;

import com.kiosco.tenant_service.domain.Store;
import com.kiosco.tenant_service.domain.Branch;
import com.kiosco.tenant_service.dto.request.StoreRequest;
import com.kiosco.tenant_service.dto.request.BranchRequest;
import com.kiosco.tenant_service.dto.response.StoreResponse;
import com.kiosco.tenant_service.dto.response.BranchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "active", ignore = true)
    Store toEntity(StoreRequest req);

    StoreResponse toResponse(Store entity);

    Branch toEntity(BranchRequest req);
    @Mapping(target = "storeId", expression = "java(branch.getStore() != null ? branch.getStore().getId() : null)")
    BranchResponse branchToResponse(Branch branch);

    default Page<StoreResponse> toResponsePage(Page<Store> stores) {
        return new PageImpl<>(
                stores.getContent()
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList()),
                stores.getPageable(),
                stores.getTotalElements()
        );
    }

    default Page<BranchResponse> toBranchResponse(Page<Branch> branches) {
        return new PageImpl<>(
                branches.getContent()
                        .stream()
                        .map(this::branchToResponse)
                        .collect(Collectors.toList()),
                branches.getPageable(),
                branches.getTotalElements()
        );
    }
}
