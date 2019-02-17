package com.da.patientmanagement.service;

import com.da.patientmanagement.dto.DTOWrapper;
import com.da.patientmanagement.dto.PatientDTO;
import com.da.patientmanagement.entity.Patient;
import com.da.patientmanagement.model.DataTablesOrder;
import com.da.patientmanagement.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public PatientDTO findById(Long id) {
        return objectMapper.convertValue(patientRepository.findById(id).orElse(new Patient()), PatientDTO.class);
    }

    public DTOWrapper findAllPageableAjax(Integer start, Integer length, Boolean showHidden, DataTablesOrder... order) {
        DTOWrapper dtoWrapper = new DTOWrapper();
        if (start != null && length != null) {
            Sort sort = new Sort(Sort.Direction.valueOf(order[0].getDir().toUpperCase()), order[0].getColumnName());
            Pageable pageable = PageRequest.of(start / length, length, sort);
            Page<Patient> patientEntities = patientRepository.findAllPageable(pageable, showHidden);
            dtoWrapper.setPatientDTOList(patientEntities
                    .stream().map(patient -> objectMapper.convertValue(patient, PatientDTO.class)).collect(Collectors.toList()));
            dtoWrapper.setTotalRecords(patientEntities.getTotalElements());
            return dtoWrapper;
        } else {
            //Complain
            return null;
        }
    }

    public boolean patientExistById(Long id) {
        return patientRepository.existsById(id);
    }

    public int softDeletePatient(Long id) {
        log.info("Soft deleting patient with id: {}", id);
        return patientRepository.softDeletePatient(id);
    }

    public int reactivatePatient(Long id) {
        log.info("Reactivating patient with id: {}", id);
        return patientRepository.reactivatePatient(id);
    }

    public PatientDTO save(PatientDTO patient) {
        Patient savedEntity = patientRepository.save(objectMapper.convertValue(patient, Patient.class));
        return objectMapper.convertValue(savedEntity, PatientDTO.class);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public DTOWrapper searchByCriteriaOrderByFirstName(String firstName, String lastName, String country, Integer start, Integer length, Boolean showHidden) {
        DTOWrapper dtoWrapper = new DTOWrapper();
        firstName = StringUtils.isNotEmpty(firstName) ? firstName : "";
        lastName = StringUtils.isNotEmpty(lastName) ? lastName : "";
        country = StringUtils.isNotEmpty(country) ? country : "";
        Pageable pageable = PageRequest.of(start / length, length);
        Page<Patient> patients = patientRepository.searchByOrCriteriaOrderByFirstName(firstName, lastName, country, pageable, showHidden);
        dtoWrapper.setPatientDTOList(patients.stream().map(patient -> objectMapper.convertValue(patient, PatientDTO.class)).collect(Collectors.toList()));
        dtoWrapper.setTotalRecords(patients.getTotalElements());
        return dtoWrapper;
    }
}
