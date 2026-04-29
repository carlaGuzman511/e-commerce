package com.kiosco.tenant_service.service;

import com.kiosco.tenant_service.config.TenantContext;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.domain.Branch;
import com.kiosco.tenant_service.exception.ResourceNotFoundException;
import com.kiosco.tenant_service.repository.BranchRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeleteBranchService {

    private final BranchRepository branchRepository;

    public DeleteBranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public void remove(Long storeId, Long branchId) {
        String tenantId = TenantContext.getCurrentTenant();

        Branch existing = branchRepository.findByIdAndStoreIdAndTenantIdAndActiveTrue(branchId, storeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.BRANCH_NOT_FOUND));

        existing.setActive(false);

        branchRepository.save(existing);
    }
}
