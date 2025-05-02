package com.example.search.controller;

import com.example.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Search API", description = "APIs for searching weather and student data")
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * Endpoint: /weather/search
     *
     * Makes parallel service calls to student-management-service and details-service
     * using CompletableFuture, merges results, and handles service failures gracefully.
     */
    @Operation(summary = "Get merged weather and student info", description = "Fetches data from student-management-service and details-service in parallel, merges results, and handles service failures.")
    @GetMapping("/weather/search")
    public ResponseEntity<?> getDetails(@Parameter(description = "Optional source identifier for testing or filtering", example = "test-source")
                                            @RequestParam(required = false) String source) {
        // Asynchronous call to student-management-service with controller-level fallback
        CompletableFuture<String> studentFuture = searchService.getStudentInfo()
                .exceptionally(ex -> "Student Service is currently unavailable (controller fallback)");

        // Asynchronous call to details-service with controller-level fallback
        CompletableFuture<Map<String, Object>> detailsFuture = searchService.getDetailsInfo()
                .exceptionally(ex -> {
                    Map<String, Object> fallback = new HashMap<>();
                    fallback.put("error", "Details Service is currently unavailable (controller fallback)");
                    return fallback;
                });

        // Merge both results after completion
        return CompletableFuture.allOf(studentFuture, detailsFuture)
                .thenApply(voided -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("student", studentFuture.join());
                    result.put("details", detailsFuture.join());

                    Map<String, Object> response = new HashMap<>();
                    response.put("code", 200);
                    response.put("timestamp", LocalDateTime.now().toString());
                    response.put("data", result);

                    return ResponseEntity.ok(response);
                })
                .exceptionally(ex -> {
                    // Global fallback: if everything fails (e.g., executor crashes)
                    Map<String, Object> error = new HashMap<>();
                    error.put("code", 500);
                    error.put("timestamp", LocalDateTime.now().toString());
                    error.put("message", "Search Service failed: " + ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
                })
                .join(); // Join to make this method return synchronously
    }
}
