package com.kiosko.authservice.domain.repository;

import com.kiosko.authservice.domain.entity.Log;
import com.kiosko.authservice.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    Page<Log> findByUser(User user, Pageable pageable);

    Page<String> findByTenantId(String tenantId, Pageable pageable);

    Page<Log> findByAction(String action, Pageable pageable);

    Page<Log> findByTableName(String tableName, Pageable pageable);

    @Query("SELECT l FROM Log l WHERE l.user = :user AND l.createdAt BETWEEN :startDate AND :endDate")
    Page<Log> findByUserAndDateRange(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT l FROM Log l WHERE l.tenantId = :tenantId AND l.createdAt BETWEEN :startDate AND :endDate")
    Page<Log> findByTenantAndDateRange(
            @Param("tenantId") String tenantId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT l FROM Log l WHERE l.action IN :actions ORDER BY l.createdAt DESC")
    List<Log> findCriticalActions(@Param("actions") List<String> actions, Pageable pageable);

    Page<Log> findByIpAddress(String ipAddress, Pageable pageable);
}