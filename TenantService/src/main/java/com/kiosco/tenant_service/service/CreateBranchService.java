package com.kiosco.tenant_service.service;

import com.kiosco.tenant_service.config.TenantContext;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.domain.Branch;
import com.kiosco.tenant_service.domain.Store;
import com.kiosco.tenant_service.dto.request.BranchRequest;
import com.kiosco.tenant_service.dto.response.BranchResponse;
import com.kiosco.tenant_service.exception.ResourceNotFoundException;
import com.kiosco.tenant_service.dto.mapper.StoreMapper;
import com.kiosco.tenant_service.repository.BranchRepository;
import com.kiosco.tenant_service.repository.StoreRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreateBranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final StoreMapper mapper;

    public CreateBranchService(
            BranchRepository branchRepository,
            StoreRepository storeRepository,
            StoreMapper mapper) {
        this.branchRepository = branchRepository;
        this.storeRepository = storeRepository;
        this.mapper = mapper;
    }

    public BranchResponse create(Long storeId, BranchRequest request) {
        String tenantId = TenantContext.getCurrentTenant();

        Store store = storeRepository.findByIdAndActiveTrue(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.STORE_NOT_FOUND));

        Branch entity = mapper.toEntity(request);
        entity.setStore(store);
        entity.setTenantId(tenantId);
        entity.setActive(true);

        Branch saved = branchRepository.save(entity);
        return mapper.branchToResponse(saved);
    }
}
