package com.da.patientmanagement.service;

import com.da.patientmanagement.dto.PatientDTO;
import com.da.patientmanagement.entity.Patient;
import com.da.patientmanagement.model.DataTablesOrder;
import com.da.patientmanagement.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<PatientDTO> findAllPageable(Integer page, Integer size) {
        List<Patient> patientEntities;
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            patientEntities = patientRepository.findAllPageable(pageable);
        } else {
            patientEntities = patientRepository.findAll();
        }

        return patientEntities
                .stream().map(patient -> objectMapper.convertValue(patient, PatientDTO.class)).collect(Collectors.toList());
    }

    public List<PatientDTO> findAllPageableAjax(Integer start, Integer length, DataTablesOrder... order) {
        List<Patient> patientEntities;
        if (start != null && length != null) {
            Sort sort = new Sort(Sort.Direction.valueOf(order[0].getDir().toUpperCase()), order[0].getColumnName());
            Pageable pageable = PageRequest.of(start/length, length, sort);
            patientEntities = patientRepository.findAllPageable(pageable);
        } else {
            patientEntities = patientRepository.findAll();
        }

        return patientEntities
                .stream().map(patient -> objectMapper.convertValue(patient, PatientDTO.class)).collect(Collectors.toList());
    }


    public List<PatientDTO> findAllByIsActive(Boolean isActive, Integer page, Integer size) {
        List<Patient> patientEntities;
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            patientEntities = patientRepository.findAllByIsActive(isActive, pageable);
        } else {
            patientEntities = patientRepository.findAll();
        }
        return patientEntities
                .stream().map(patient -> objectMapper.convertValue(patient, PatientDTO.class)).collect(Collectors.toList());

    }

    public boolean patientExistById(Long id) {
        return patientRepository.existsById(id);
    }

    public int softDeletePatient(Long id) {
        log.info("Soft deleting patient with id: {}", id);
        return patientRepository.softDeletePatient(id);
    }

    public PatientDTO save(PatientDTO patient) {
        Patient savedEntity = patientRepository.save(objectMapper.convertValue(patient, Patient.class));
        return objectMapper.convertValue(savedEntity, PatientDTO.class);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<PatientDTO> searchByCriteriaOrderByFirstName(String firstName, String lastName, String country, Integer start, Integer length) {
        firstName = StringUtils.isNotEmpty(firstName) ? firstName : "";
        lastName = StringUtils.isNotEmpty(lastName) ? lastName : "";
        country = StringUtils.isNotEmpty(country) ? country : "";
        Pageable pageable = PageRequest.of(start/length, length);
        return patientRepository.searchByOrCriteriaOrderByFirstName(firstName, lastName, country, pageable)
                .stream().map(patient -> objectMapper.convertValue(patient, PatientDTO.class)).collect(Collectors.toList());
    }
}
