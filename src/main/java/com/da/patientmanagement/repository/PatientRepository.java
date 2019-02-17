package com.da.patientmanagement.repository;

import com.da.patientmanagement.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.isHidden = :showHidden")
    Page<Patient> findAllPageable(Pageable pageable, @Param("showHidden") Boolean showHidden);

    @Query("SELECT p FROM Patient p JOIN p.address a WHERE " +
            "((:firstName = '' or lower(p.firstName) LIKE lower(concat('%',:firstName,'%'))) OR " +
            "(:lastName = '' or lower(p.lastName) LIKE lower(concat('%',:lastName,'%'))) OR " +
            "(:country = '' or lower(a.country) LIKE lower(concat('%',:country,'%')))) AND " +
            "p.isHidden = :showHidden " +
            "ORDER BY p.firstName")
    Page<Patient> searchByOrCriteriaOrderByFirstName(@Param("firstName") String firstName,
                                                     @Param("lastName") String lastName,
                                                     @Param("country") String country, Pageable pageable,
                                                     @Param("showHidden") Boolean showHidden);

    @Query("update Patient p set p.isHidden = true where p.id = :id")
    @Modifying
    int softDeletePatient(@Param("id") Long id);

    @Query("update Patient p set p.isHidden = false where p.id = :id")
    @Modifying
    int reactivatePatient(@Param("id") Long id);

    @Override
    void delete(Patient patient);
}
