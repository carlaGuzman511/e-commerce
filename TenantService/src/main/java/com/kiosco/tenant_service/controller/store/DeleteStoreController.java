package com.kiosco.tenant_service.controller.store;

import com.kiosco.tenant_service.constants.ApiPath;
import com.kiosco.tenant_service.service.DeleteStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.Store.BASE)
public class DeleteStoreController {
    private final DeleteStoreService deleteStoreService;

    public DeleteStoreController(DeleteStoreService deleteStoreService) {
        this.deleteStoreService = deleteStoreService;
    }

    @DeleteMapping(ApiPath.Store.BY_ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteStoreService.remove(id);

        return ResponseEntity.noContent().build();
    }
}