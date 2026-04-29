package com.kiosco.tenant_service.dto.response;

import com.kiosco.tenant_service.domain.Address;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreResponse {
    private Long id;

    private String name;

    private Boolean active;

    private String currency;

    private String defaultLanguage;

    private String timezone;

    private String ownerName;

    private String ownerInfo;

    private String info;

    private Address address;

    private List<BranchResponse> branches;

    private String tenantId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}