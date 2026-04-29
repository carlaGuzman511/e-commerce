package com.kiosko.media_service.dto;

import com.kiosko.media_service.enums.OwnerType;
import lombok.*;


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
