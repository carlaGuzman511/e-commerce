package com.kiosco.tenant_service.service;

import com.kiosco.tenant_service.config.TenantContext;
import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.domain.Store;
import com.kiosco.tenant_service.dto.mapper.StoreMapper;
import com.kiosco.tenant_service.dto.request.StoreRequest;
import com.kiosco.tenant_service.dto.response.StoreResponse;
import com.kiosco.tenant_service.exception.ResourceNotFoundException;
import com.kiosco.tenant_service.interfaces.generics.IUpdate;
import com.kiosco.tenant_service.repository.StoreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpdateStoreService implements IUpdate<StoreRequest, StoreResponse, Long> {

    private final StoreRepository storeRepository;
    private final StoreMapper mapper;

    public UpdateStoreService(StoreRepository storeRepository, StoreMapper mapper) {
        this.storeRepository = storeRepository;
        this.mapper = mapper;
    }
    @Override
    public StoreResponse update(Long id, StoreRequest req) {
        String tenantId = TenantContext.getCurrentTenant();

        Store existing = storeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.STORE_NOT_FOUND));

        mapper.toEntity(req);
        BeanUtils.copyProperties(req, existing);

        Store saved = storeRepository.save(existing);

        return mapper.toResponse(saved);
    }
}
