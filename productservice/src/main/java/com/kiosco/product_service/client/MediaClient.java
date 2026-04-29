package com.kiosco.product_service.client;

import com.kiosco.product_service.constants.ApiPaths;
import com.kiosco.product_service.dto.ApiResponse;
import com.kiosco.product_service.dto.request.PublicIdsRequest;
import com.kiosco.product_service.dto.response.MediaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "media-service", url = "${services.media.url}")
public interface MediaClient {

    @PostMapping(ApiPaths.Media.BATCH)
    ApiResponse<List<MediaResponse>> getByPublicIds(@RequestBody PublicIdsRequest request);
}
