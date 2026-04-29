package com.kiosco.tenant_service.service;

import com.kiosco.tenant_service.domain.Store;
import com.kiosco.tenant_service.dto.request.StoreRequest;
import com.kiosco.tenant_service.dto.response.StoreResponse;
import com.kiosco.tenant_service.dto.mapper.StoreMapper;
import com.kiosco.tenant_service.interfaces.generics.ICreate;
import com.kiosco.tenant_service.repository.StoreRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreateStoreService implements ICreate<StoreResponse, StoreRequest> {

    private final StoreRepository storeRepository;
    private final StoreMapper mapper;

    public CreateStoreService(StoreRepository storeRepository, StoreMapper mapper) {
        this.storeRepository = storeRepository;
        this.mapper = mapper;
    }

    @Override
    public StoreResponse create(StoreRequest req) {
        Store entity = mapper.toEntity(req);
        String tenantId = UUID.randomUUID().toString();

        entity.setTenantId(tenantId);
        entity.setActive(true);

        if (entity.getBranches() != null) {
            entity.getBranches().forEach(branch -> {
                branch.setStore(entity);
                branch.setTenantId(tenantId);
                branch.setActive(true);
            });
        }

        Store saved = storeRepository.save(entity);
        StoreResponse storeResponse = mapper.toResponse(saved);

        return storeResponse;
    }
}
