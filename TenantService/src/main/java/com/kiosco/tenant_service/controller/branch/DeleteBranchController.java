package com.kiosco.tenant_service.controller.branch;

import com.kiosco.tenant_service.constants.ApiPath;
import com.kiosco.tenant_service.service.DeleteBranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.Branch.BASE)
public class DeleteBranchController {
    private final DeleteBranchService deleteBranchService;
    private Long storeId;

    @ModelAttribute
    public void setStoreId(@PathVariable Long storeId) {
        this.storeId = storeId;
    }

    public DeleteBranchController(DeleteBranchService deleteBranchService) {
        this.deleteBranchService = deleteBranchService;
    }

    @DeleteMapping(ApiPath.Branch.BY_ID)
    public ResponseEntity<Void> delete(
            @PathVariable Long branchId) {
        deleteBranchService.remove(storeId, branchId);

        return ResponseEntity.noContent().build();
    }
}
