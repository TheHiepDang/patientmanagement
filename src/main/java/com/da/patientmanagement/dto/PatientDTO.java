package com.da.patientmanagement.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "address")
public class PatientDTO {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String contactNo;
    private List<AddressDTO> address;
    private boolean isActive = true;
    private boolean isHidden = false;
}
