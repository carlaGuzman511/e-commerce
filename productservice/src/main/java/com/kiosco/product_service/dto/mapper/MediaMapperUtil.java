package com.kiosco.product_service.dto.mapper;

import com.kiosco.product_service.client.MediaClient;
import com.kiosco.product_service.dto.response.MediaResponse;
import com.kiosco.product_service.dto.response.ProductResponse;
import com.kiosco.product_service.dto.response.ProductVariantResponse;
import com.kiosco.product_service.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class MediaMapperUtil {

    public static void mapMedia(ProductResponse response, Product product, MediaClient mediaClient) {
        for (ProductVariantResponse vr : response.getVariants()) {
            var variant = product.getVariants().stream()
                    .filter(v -> v.getId().equals(vr.getId()))
                    .findFirst()
                    .orElse(null);

            if (variant == null || CollectionUtils.isEmpty(variant.getImages())) continue;

            try {
                List<MediaResponse> media = mediaClient.getByPublicIds(
                                new com.kiosco.product_service.dto.request.PublicIdsRequest(variant.getImages()))
                        .getData();

                vr.setImages(media);
            } catch (Exception ex) {
                log.warn("Error fetching media for variant {} - {}", vr.getProductCode(), ex.getMessage());
                vr.setImages(List.of());
            }
        }
    }
}
