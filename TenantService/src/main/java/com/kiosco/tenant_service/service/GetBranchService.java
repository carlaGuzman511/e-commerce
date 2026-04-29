package com.kiosco.tenant_service.service;

import com.kiosco.tenant_service.config.TenantContext;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.domain.Branch;
import com.kiosco.tenant_service.dto.mapper.StoreMapper;
import com.kiosco.tenant_service.dto.response.BranchResponse;
import com.kiosco.tenant_service.exception.ResourceNotFoundException;
import com.kiosco.tenant_service.repository.BranchRepository;
import com.kiosco.tenant_service.repository.specification.BranchSpecification;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


@Service
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetBranchService {
    private final BranchRepository branchRepository;
    private final StoreMapper mapper;
    public GetBranchService(BranchRepository branchRepository, StoreMapper mapper) {
        this.branchRepository = branchRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public BranchResponse getById(Long storeId, Long branchId) {
        String tenantId = TenantContext.getCurrentTenant();

        Branch branch = branchRepository.findByIdAndStoreIdAndTenantIdAndActiveTrue(branchId, storeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.BRANCH_NOT_FOUND));

        return mapper.branchToResponse(branch);
    }

    public Page<BranchResponse> getAll(Long storeId, Pageable pageable, Boolean active, Instant startDate, Instant endDate) {
        String tenantId = TenantContext.getCurrentTenant();
        Specification<Branch> spec = Specification
                .where(BranchSpecification.hasTenantId(tenantId))
                .and(BranchSpecification.hasStoreId(storeId))
                .and(BranchSpecification.hasStatus(active))
                .and(BranchSpecification.createdBetween(startDate, endDate));

        Page<Branch> page = branchRepository.findAll(spec, pageable);

        return mapper.toBranchResponse(page);
    }
}