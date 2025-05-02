package com.antra.studentmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for Student")
public class StudentDTO {

    @Schema(description = "Student's full name", example = "Alice Smith")
    private String name;

    @Schema(description = "Student's email address", example = "alice@example.com")
    private String email;

    @Schema(description = "Student's age in years", example = "20")
    private int age;

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
