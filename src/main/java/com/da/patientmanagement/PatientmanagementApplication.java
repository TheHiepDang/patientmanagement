package com.da.patientmanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
@Slf4j
public class PatientmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientmanagementApplication.class, args);
	}
}

