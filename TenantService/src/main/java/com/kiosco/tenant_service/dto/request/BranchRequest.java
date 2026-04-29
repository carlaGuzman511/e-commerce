package com.kiosco.tenant_service.dto.request;

import com.kiosco.tenant_service.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequest {
    @NotBlank
    @Size(max = 200)
    private String name;

    private Address address;

    @Size(max = 100)
    private String managerName;

    @Size(max = 30)
    private String managerPhone;

    private String openingHours;
    
    private String closingHours;
}