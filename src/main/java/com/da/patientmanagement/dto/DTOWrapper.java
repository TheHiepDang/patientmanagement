package com.da.patientmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class DTOWrapper {
    Long totalRecords;
    List<PatientDTO> patientDTOList;
}
