package com.kiosco.product_service.service;

import com.kiosco.product_service.client.MediaClient;
import com.kiosco.product_service.domain.ProductVariant;
import com.kiosco.product_service.dto.request.PublicIdsRequest;
import com.kiosco.product_service.dto.response.MediaResponse;
import com.kiosco.product_service.dto.response.ProductVariantResponse;
import com.kiosco.product_service.exeption.NotFoundException;
import com.kiosco.product_service.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl {

    private final ProductVariantRepository variantRepository;
    private final MediaClient mediaClient;

    public ProductVariantResponse getById(Long id) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Variant not found"));

        ProductVariantResponse response = new ProductVariantResponse();
        response.setId(variant.getId());
        response.setProductCode(variant.getProductCode());
        response.setDescription(variant.getDescription());
        response.setPrice(variant.getPrice());
        response.setAttributesJson(variant.getAttributes());

        if (variant.getImages() != null && !variant.getImages().isEmpty()) {
            try {
                PublicIdsRequest publicIdsRequest = new PublicIdsRequest();
                publicIdsRequest.setPublicIds(variant.getImages());
                List<MediaResponse> medias = mediaClient.getByPublicIds(publicIdsRequest).getData();
                response.setImages(medias);
            } catch (Exception e) {
                System.err.println("Error llamando a media-service: " + e.getMessage());
                response.setImages(List.of());
            }
        }
        return response;
    }
}