package com.da.patientmanagement.repository;

import com.da.patientmanagement.entity.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.isHidden = false")
    List<Patient> findAllPageable(Pageable pageable);

    @Query("SELECT p FROM Patient p ORDER BY :col")
    List<Patient> findAllPageableWithOrder(Pageable pageable, @Param("col") String col);

    List<Patient> findAllByIsActive(Boolean isActive, Pageable pageable);

    @Query("SELECT p FROM Patient p JOIN p.address a WHERE " +
            "(:firstName = '' or p.firstName = :firstName) and " +
            "(:lastName = '' or p.lastName = :lastName) and " +
            "(:country = '' or a.country = :country) " +
            "ORDER BY p.firstName")
    List<Patient> searchByAndCriteriaOrderByFirstName(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("country") String country, Pageable pageable);

    @Query("SELECT p FROM Patient p JOIN p.address a WHERE " +
            "((:firstName = '' or lower(p.firstName) LIKE lower(concat('%',:firstName,'%'))) OR " +
            "(:lastName = '' or lower(p.lastName) LIKE lower(concat('%',:lastName,'%'))) OR " +
            "(:country = '' or lower(a.country) LIKE lower(concat('%',:country,'%')))) AND " +
            "p.isHidden = false " +
            "ORDER BY p.firstName")
    List<Patient> searchByOrCriteriaOrderByFirstName(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("country") String country, Pageable pageable);

    @Query("update Patient p set p.isHidden = true where p.id = :id")
    @Modifying
    int softDeletePatient(@Param("id") Long id);

    @Override
    void delete(Patient patient);
}
