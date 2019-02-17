package com.da.patientmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PATIENT")
@Data
@ToString(exclude = "address")
@EqualsAndHashCode(exclude = "address")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "CONTACTNO")
    private String contactNo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<Address> address;
    @Column(name = "ISHIDDEN")
    private Boolean isHidden;

    public void setAddress(List<Address> address) {
        this.address = address;
        address.forEach(address1 -> address1.setPatient(this));
    }
}
