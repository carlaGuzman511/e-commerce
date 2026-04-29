package com.kiosko.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenInfo {

    private String token;
    private DecodedHeader header;
    private DecodedPayload payload;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DecodedHeader {
        private String alg;
        private String typ;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DecodedPayload {
        private String sub;
        private String iss;
        private Long iat;
        private Long exp;
        private List<String> authorities;
    }
}