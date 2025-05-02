package com.example.search.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    // ExecutorService is used to run the blocking Hystrix calls in parallel threads
    // CompletableFuture.supplyAsync() will use this thread pool
    private final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * This method wraps the Hystrix-protected student info call inside a CompletableFuture.
     * It runs asynchronously using the executor defined above.
     */
    public CompletableFuture<String> getStudentInfo() {
        return CompletableFuture.supplyAsync(this::getStudentInfoInternal, executor);
    }//CompletableFuture.supplyAsync() is used to run the Hystrix-protected method on a new thread.

    /**
     * This method wraps the Hystrix-protected details info call inside a CompletableFuture.
     * It also runs asynchronously.
     */
    public CompletableFuture<Map<String, Object>> getDetailsInfo() {
        return CompletableFuture.supplyAsync(this::getDetailsInfoInternal, executor);
    }

    /**
     * Hystrix-protected method that calls the student-management-service.
     * If the service is down or times out, it will trigger the fallback method.
     */
    @HystrixCommand(fallbackMethod = "fallbackStudentInfo")
    public String getStudentInfoInternal() {
        try {
            return restTemplate.getForObject("http://student-management-service/students/port", String.class);
        } catch (Exception ex) {
            // Re-throw so Hystrix can trigger fallback
            throw new RuntimeException("Downstream call to student-management-service failed", ex);
        }
    }


    /**
     * Fallback method for student info call.
     * This method is called automatically if the above method fails.
     */
    public String fallbackStudentInfo() {
        return "Student Service is currently unavailable.";
    }

    /**
     * Hystrix-protected method that calls the details-service.
     * Same circuit-breaker logic applies as above.
     */
    @HystrixCommand(fallbackMethod = "fallbackDetailsInfo")
    public Map<String, Object> getDetailsInfoInternal() {
        try {
            return restTemplate.getForObject("http://details/details/port", Map.class);
        } catch (Exception ex) {
            // This triggers Hystrix fallback
            throw new RuntimeException("Downstream call to details-service failed", ex);
        }
    }


    /**
     * Fallback method for details info call.
     * Triggered when the above method fails.
     */
    public Map<String, Object> fallbackDetailsInfo() {
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("error", "Details Service is currently unavailable.");
        return fallback;
    }
}
