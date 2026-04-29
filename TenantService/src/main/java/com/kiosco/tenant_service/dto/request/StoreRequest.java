package com.kiosco.tenant_service.dto.request;

import com.kiosco.tenant_service.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreRequest {
    @NotBlank
    @Size(max = 200)
    private String name;

    private String currency;

    private String defaultLanguage;
    
    private String timezone;

    @Size(max = 200)
    private String ownerName;

    @Size(max = 2000)
    private String ownerInfo;

    @Size(max = 4000)
    private String info;

    private Address address;
}
