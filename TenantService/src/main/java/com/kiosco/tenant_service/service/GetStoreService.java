package com.kiosco.tenant_service.service;

import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.domain.Store;
import com.kiosco.tenant_service.dto.mapper.StoreMapper;
import com.kiosco.tenant_service.dto.response.StoreResponse;
import com.kiosco.tenant_service.exception.ResourceNotFoundException;
import com.kiosco.tenant_service.interfaces.generics.IRead;
import com.kiosco.tenant_service.repository.StoreRepository;
import com.kiosco.tenant_service.repository.specification.StoreSpecification;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetStoreService implements IRead<StoreResponse, Long> {
    private final StoreRepository storeRepository;
    private final StoreMapper mapper;

    public GetStoreService(StoreRepository storeRepository, StoreMapper mapper) {
        this.storeRepository = storeRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
        public StoreResponse getById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                        .orElseThrow(() -> new ResourceNotFoundException(Messages.STORE_NOT_FOUND));

        return mapper.toResponse(store);
    }

    public Page<StoreResponse> getAll(Pageable pageable, Boolean active, String tenantId, Instant startDate, Instant endDate) {
        Specification<Store> spec = Specification
                .where(StoreSpecification.hasStatus(active))
                .and(StoreSpecification.hasTenantId(tenantId))
                .and(StoreSpecification.createdBetween(startDate, endDate));

        Page<Store> page = storeRepository.findAll(spec, pageable);

        return page.map(mapper::toResponse);
    }
}
