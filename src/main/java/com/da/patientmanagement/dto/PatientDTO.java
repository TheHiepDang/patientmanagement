package com.da.patientmanagement.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = "address")
public class PatientDTO {
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String contactNo;
    private List<AddressDTO> address;
    private Boolean isHidden = false;
}
