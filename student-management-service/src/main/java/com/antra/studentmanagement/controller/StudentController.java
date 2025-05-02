package com.antra.studentmanagement.controller;

import com.antra.studentmanagement.dto.StudentDTO;
import com.antra.studentmanagement.entity.Student;
import com.antra.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Student API", description = "APIs for managing student records")
@RestController
@RequestMapping("/students")
@CrossOrigin("http://localhost:4200")
public class StudentController {
    @Autowired
    private final StudentService service;

    private final WebServerApplicationContext webServerAppCtxt;

    @Autowired
    public StudentController(StudentService service, WebServerApplicationContext webServerAppCtxt) {
        this.service = service;
        this.webServerAppCtxt = webServerAppCtxt;
    }

//    public StudentController(StudentService service) {
//        this.service = service;
//    }

    @Operation(summary = "Create a new student")
    @PostMapping
    public Student createStudent(@RequestBody StudentDTO dto) {
        return service.createStudent(dto);
    }

    @Operation(summary = "Get all students")
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @Operation(summary = "Get student by ID")
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @Operation(summary = "Update student by ID")
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody StudentDTO dto) {
        return service.updateStudent(id, dto);
    }

    @Operation(summary = "Delete student by ID")
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
    }

    //Endpoint for SearchService to call
    @Operation(summary = "Get the service's running port")
    @GetMapping("/port")
    public String getServicePort() {
        int port = webServerAppCtxt.getWebServer().getPort();
        return "Student Management Service running on port: " + port;
    }
}
