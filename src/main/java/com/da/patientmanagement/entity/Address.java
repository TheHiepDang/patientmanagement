package com.da.patientmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
@Data
@ToString(exclude = "patient")
@EqualsAndHashCode(exclude = "patient")
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ADDRESSLINE1")
    private String addressLine1;
    @Column(name = "ADDRESSLINE2")
    private String addressLine2;
    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "POSTALCODE")
    private String postalCode;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonBackReference
    private Patient patient;
}
