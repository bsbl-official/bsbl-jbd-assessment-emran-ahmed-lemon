package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EducationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationManagementApplication.class, args);
    }
}
