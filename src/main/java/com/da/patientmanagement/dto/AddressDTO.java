package com.da.patientmanagement.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = "patient")
@EqualsAndHashCode(exclude = "patient")
public class AddressDTO {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private String postalCode;
}
