package com.kiosco.product_service.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicIdsRequest {

    private List<String> publicIds;
}
