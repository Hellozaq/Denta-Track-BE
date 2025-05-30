package com.asusoftware.DentaTrack_Backend.user.repository;

import com.asusoftware.DentaTrack_Backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Caută un utilizator după UUID-ul din Keycloak.
     */
    Optional<User> findByKeycloakId(UUID keycloakId);

    /**
     * Caută toți asistenții legați de un doctor anume.
     */
    List<User> findByDoctorId(UUID doctorId);

    /**
     * Verifică dacă un user cu rolul dat este deja înregistrat cu un anumit Keycloak ID.
     */
    boolean existsByKeycloakIdAndRole(UUID keycloakId, String role);

    /**
     * Returnează toți utilizatorii DOCTOR sau ASSISTANT legați de o clinică,
     * prin join cu tabela clinic_owners (pentru dashboard admin).
     */
    @Query("""
        SELECT u FROM User u
        WHERE u.id IN (
            SELECT co.userId FROM ClinicOwner co WHERE co.clinicId = :clinicId
        )
        OR u.doctorId IN (
            SELECT co.userId FROM ClinicOwner co WHERE co.clinicId = :clinicId
        )
    """)
    List<User> findAllUsersByClinic(@Param("clinicId") UUID clinicId);

    @Query("SELECT co.clinicId FROM ClinicOwner co WHERE co.userId = :userId")
    List<UUID> findClinicIdsWhereUserIsOwner(@Param("userId") UUID userId);

    Optional<User> findByEmail(String email);


    @Query("""
    SELECT u
    FROM User u
    JOIN ClinicStaff cs ON cs.userId = u.id
    WHERE cs.clinicId = :clinicId
""")
    List<User> findUsersByClinic(UUID clinicId);

    @Query("""
    SELECT u FROM User u 
    WHERE u.id IN (
        SELECT cs.userId FROM ClinicStaff cs WHERE cs.clinicId = :clinicId
    )
""")
    List<User> findUsersByClinicViaClinicStaff(@Param("clinicId") UUID clinicId);


}

