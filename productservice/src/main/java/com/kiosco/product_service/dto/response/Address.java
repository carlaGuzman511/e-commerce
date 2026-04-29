package com.kiosco.product_service.dto.response;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String state;
}
