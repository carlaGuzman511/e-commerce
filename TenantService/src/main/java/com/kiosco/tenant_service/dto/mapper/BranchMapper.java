package com.kiosco.tenant_service.dto.mapper;

import com.kiosco.tenant_service.domain.Branch;
import com.kiosco.tenant_service.dto.request.BranchRequest;
import com.kiosco.tenant_service.dto.response.BranchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    Branch toEntity(BranchRequest req);

    BranchResponse toResponse(Branch entity);
    @Mapping(target = "storeId", expression = "java(branch.getStore() != null ? branch.getStore().getId() : null)")
    BranchResponse branchToResponse(Branch branch);

    default Page<BranchResponse> toBranchResponse(Page<Branch> branches) {
        return new PageImpl<>(
                branches.getContent()
                        .stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList()),
                branches.getPageable(),
                branches.getTotalElements()
        );
    }
}
