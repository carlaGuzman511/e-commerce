package com.kiosko.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleListResponse {

    private Long id;
    private String name;
    private String description;
    private Integer userCount;
    private Integer permissionCount;
    private LocalDateTime createdAt;
}