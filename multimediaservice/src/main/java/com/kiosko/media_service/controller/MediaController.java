package com.kiosko.media_service.controller;

import com.kiosko.media_service.constants.ApiPaths;
import com.kiosko.media_service.constants.Messages;
import com.kiosko.media_service.dto.ApiResponse;
import com.kiosko.media_service.dto.MediaResponse;
import com.kiosko.media_service.dto.PublicIdsRequest;
import com.kiosko.media_service.enums.OwnerType;
import com.kiosko.media_service.service.MediaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.Media.BASE)
@RequiredArgsConstructor
public class MediaController {

    private final MediaServiceImpl mediaService;

    @PostMapping(ApiPaths.Media.UPLOAD)
    public ResponseEntity<ApiResponse<MediaResponse>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "products") String folder,
            @RequestParam(defaultValue = "PRODUCT") OwnerType ownerType,
            @RequestParam(required = false) Integer orderIndex
    ) {
        MediaResponse response = mediaService.uploadAndSave(file, folder, ownerType, orderIndex);

        ApiResponse<MediaResponse> apiResponse = ApiResponse.<MediaResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.MEDIA_UPLOADED)
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MediaResponse>>> list(@RequestParam(required = false) OwnerType ownerType) {
        List<MediaResponse> responseList = mediaService.findAll(ownerType);

        ApiResponse<List<MediaResponse>> apiResponse = ApiResponse.<List<MediaResponse>>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.MEDIA_LIST_RETRIEVED)
                .data(responseList)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String publicId) {
        mediaService.delete(publicId);

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.MEDIA_DELETED)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping(ApiPaths.Media.BATCH)
    public ResponseEntity<ApiResponse<List<MediaResponse>>> getByPublicIds(@RequestBody PublicIdsRequest body) {
        List<MediaResponse> list = mediaService.getByPublicIds(body.getPublicIds());
        ApiResponse<List<MediaResponse>> api = ApiResponse.<List<MediaResponse>>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.MEDIA_LIST_RETRIEVED)
                .data(list)
                .build();
        return ResponseEntity.ok(api);
    }
}
