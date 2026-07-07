package edu.cit.garol.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "edu.cit.garol.campus")
public class CampusApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusApplication.class, args);
	}

}

