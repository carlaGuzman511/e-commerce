package com.kiosco.product_service.dto.response;

import com.kiosco.product_service.enums.OwnerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MediaResponse {

    private String name;

    private String publicId;

    private String url;

    private String folder;

    private OwnerType ownerType;

    private Integer orderIndex;
}
