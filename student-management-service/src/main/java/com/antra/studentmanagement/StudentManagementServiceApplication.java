package com.antra.studentmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class StudentManagementServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(StudentManagementServiceApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(StudentManagementServiceApplication.class, args);
        logger.info("✅ Student Management Service started and logging is working!");
    }

}
