package com.kiosco.tenant_service.service;

import com.kiosco.tenant_service.config.TenantContext;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.domain.Branch;
import com.kiosco.tenant_service.dto.mapper.StoreMapper;
import com.kiosco.tenant_service.dto.request.BranchRequest;
import com.kiosco.tenant_service.dto.response.BranchResponse;
import com.kiosco.tenant_service.exception.ResourceNotFoundException;
import com.kiosco.tenant_service.repository.BranchRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpdateBranchService {

    private final BranchRepository branchRepository;
    private final StoreMapper mapper;

    public UpdateBranchService(BranchRepository branchRepository, StoreMapper mapper) {
        this.branchRepository = branchRepository;
        this.mapper = mapper;
    }

    public BranchResponse update(Long storeId, Long branchId, BranchRequest req) {
        String tenantId = TenantContext.getCurrentTenant();

        Branch existing = branchRepository.findByIdAndStoreIdAndTenantIdAndActiveTrue(branchId, storeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.BRANCH_NOT_FOUND));

        BeanUtils.copyProperties(req, existing);

        Branch saved = branchRepository.save(existing);
        return mapper.branchToResponse(saved);
    }
}