package com.asusoftware.DentaTrack_Backend.inventoryLog.repository;

import com.asusoftware.DentaTrack_Backend.inventoryLog.model.InventoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, UUID> {

    /**
     * Returnează toate modificările de stoc pentru un anumit produs.
     */
    List<InventoryLog> findByProductId(UUID productId);

    /**
     * Returnează toate logurile efectuate de un utilizator.
     */
    List<InventoryLog> findByUserId(UUID userId);

    List<InventoryLog> findByClinicId(UUID clinicId);

    List<InventoryLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    List<InventoryLog> findByClinicIdAndTimestampBetween(UUID clinicId, LocalDateTime start, LocalDateTime end);

    int countByClinicIdAndActionTypeAndTimestampBetween(UUID clinicId, String actionType, LocalDateTime start, LocalDateTime end);


}
