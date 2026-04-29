package com.kiosco.product_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponse {
    private Long id;

    private Long storeId;

    private String name;

    private Boolean active;

    private Address address;

    private String managerName;

    private String managerPhone;

    private String openingHours;

    private String closingHours;

    private String tenantId;

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
