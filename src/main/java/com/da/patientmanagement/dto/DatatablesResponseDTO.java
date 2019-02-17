package com.da.patientmanagement.dto;

import lombok.Data;

@Data
public class DatatablesResponseDTO {
    private Object[] data;
    private Integer draw;
    private Integer recordsTotal;
    private Long recordsFiltered;
}
