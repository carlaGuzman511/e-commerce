package com.kiosko.media_service.service;

import com.kiosko.media_service.dto.MediaResponse;
import com.kiosko.media_service.enums.OwnerType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaResponse uploadAndSave(MultipartFile file, String folder, OwnerType ownerType, Integer orderIndex);

    List<MediaResponse> findAll(OwnerType ownerType);

    void delete(String id);

    List<MediaResponse> getByPublicIds(List<String> ids);
}