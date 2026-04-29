package com.kiosko.media_service.repository;

import com.kiosko.media_service.domain.Media;
import com.kiosko.media_service.enums.OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllByOwnerType(OwnerType ownerType);

    List<Media> findAllByPublicIdInAndTenantId(List<String> publicIds, String tenantId);
    Optional<Media> findByPublicIdAndTenantId(String publicId, String tenantId);
}