package com.da.patientmanagement;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@EntityScan
@Slf4j
public class PatientmanagementApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PatientmanagementApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PatientmanagementApplication.class, args);
	}

//	@Bean
//	public FirebaseAuth firebaseAuth() throws IOException, FirebaseAuthException {
//		FirebaseOptions options = new FirebaseOptions.Builder()
//				.setCredentials(GoogleCredentials.fromStream(new ClassPathResource("fbcreds.json").getInputStream()))
//				.setDatabaseUrl("https://fboauth-c5c7e.firebaseio.com").build();
//
//		FirebaseApp.initializeApp(options);
//		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//		log.info("Sample token: {}", FirebaseAuth.getInstance().createCustomToken("OcpKK5h053TpS3i5KjsDq9gzcT13"));
//		return firebaseAuth;
//	}

}

