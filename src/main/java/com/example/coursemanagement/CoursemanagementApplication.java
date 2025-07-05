package com.example.coursemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaAuditing
public class CoursemanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoursemanagementApplication.class, args);
	}

}
