package com.kiosco.tenant_service.service;

import com.kiosco.tenant_service.constants.Messages;
import com.kiosco.tenant_service.domain.Store;
import com.kiosco.tenant_service.exception.ResourceNotFoundException;
import com.kiosco.tenant_service.interfaces.generics.IRemove;
import com.kiosco.tenant_service.repository.StoreRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeleteStoreService implements IRemove<Long> {
    private final StoreRepository storeRepository;

    public DeleteStoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public void remove(Long id) {
        Store existing = storeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.STORE_NOT_FOUND));

        existing.setActive(false);

        storeRepository.save(existing);
    }
}
