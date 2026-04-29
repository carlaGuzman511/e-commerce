package com.kiosko.media_service.service;

import com.kiosko.media_service.config.TenantContext;
import com.kiosko.media_service.constants.Messages;
import com.kiosko.media_service.domain.Media;
import com.kiosko.media_service.dto.MediaResponse;
import com.kiosko.media_service.enums.OwnerType;
import com.kiosko.media_service.exeption.BusinessException;
import com.kiosko.media_service.exeption.NotFoundException;
import com.kiosko.media_service.provider.CloudinaryStorageProvider;
import com.kiosko.media_service.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final CloudinaryStorageProvider cloudinary;
    private final MediaRepository mediaRepository;

    @Override
    public MediaResponse uploadAndSave(MultipartFile file, String folder, OwnerType ownerType, Integer orderIndex) {
        Map<String, Object> upload = cloudinary.uploadAndReturn(file, folder);
        String tenantId = TenantContext.get();
        Media media = new Media();
        media.setName(file.getOriginalFilename());
        media.setUrl(upload.get("secure_url").toString());
        media.setPublicId(upload.get("public_id").toString());
        media.setFolder(folder);
        media.setOwnerType(ownerType);
        media.setOrderIndex(orderIndex);
        media.setTenantId(tenantId);
        mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .url(media.getUrl())
                .publicId(media.getPublicId())
                .folder(media.getFolder())
                .ownerType(media.getOwnerType())
                .orderIndex(media.getOrderIndex())
                .build();
    }

    @Override
    public List<MediaResponse> findAll(OwnerType ownerType) {
        var list = ownerType == null ?
                mediaRepository.findAll() :
                mediaRepository.findAllByOwnerType(ownerType);

        return list.stream()
                .map(m -> MediaResponse.builder()
                        .name(m.getName())
                        .publicId(m.getPublicId())
                        .url(m.getUrl())
                        .folder(m.getFolder())
                        .ownerType(m.getOwnerType())
                        .orderIndex(m.getOrderIndex())
                        .build())
                .toList();
    }

    @Override
    public void delete(String publicId) {
        String tenantId = TenantContext.get();

        Media media = mediaRepository.findByPublicIdAndTenantId(publicId, tenantId)
                .orElseThrow(() -> new NotFoundException(Messages.MEDIA_NOT_FOUND));

        if (cloudinary.delete(publicId)) {
            mediaRepository.delete(media);
        } else {
            throw new BusinessException(Messages.CLOUDINARY_DELETE_FAILED + ": " + publicId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaResponse> getByPublicIds(List<String> publicIds) {
        if (publicIds == null || publicIds.isEmpty()) {
            return List.of();
        }

        String tenantId = TenantContext.get();
        List<Media> mediaList = mediaRepository.findAllByPublicIdInAndTenantId(publicIds, tenantId);

        return mediaList.stream()
                .map(this::toResponse)
                .toList();
    }

    private MediaResponse toResponse(Media m) {
        return MediaResponse.builder()
                .name(m.getName())
                .url(m.getUrl())
                .publicId(m.getPublicId())
                .folder(m.getFolder())
                .ownerType(m.getOwnerType())
                .orderIndex(m.getOrderIndex())
                .build();
    }
}