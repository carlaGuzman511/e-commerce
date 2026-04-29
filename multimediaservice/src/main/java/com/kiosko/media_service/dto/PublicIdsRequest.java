package com.kiosko.media_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class PublicIdsRequest {
    @NotEmpty
    private List<String> publicIds;
}
