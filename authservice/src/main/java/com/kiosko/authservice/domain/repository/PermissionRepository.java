package com.kiosko.authservice.domain.repository;

import com.kiosko.authservice.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByResourceAndAction(String resource, String action);

    boolean existsByResourceAndAction(String resource, String action);

    List<Permission> findByResource(String resource);

    List<Permission> findByAction(String action);

    List<Permission> findByResourceIn(List<String> resources);
}