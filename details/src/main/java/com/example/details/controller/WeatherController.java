package com.example.details.controller;

import com.example.details.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "Weather API", description = "APIs for weather data")
@RefreshScope
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    private final WebServerApplicationContext webServerAppCtxt;

    @Autowired
    public WeatherController(WeatherService weatherService, WebServerApplicationContext webServerAppCtxt) {
        this.weatherService = weatherService;
        this.webServerAppCtxt = webServerAppCtxt;
    }

//    @Autowired
//    public WeatherController(WeatherService weatherService) {
//        this.weatherService = weatherService;
//    }
    @Operation(summary = "Get weather info by city name")
    @GetMapping("/details")
    public ResponseEntity<?> queryWeatherByCity(@RequestParam(required = true) String city) {
        return new ResponseEntity<>(weatherService.findCityIdByName(city), HttpStatus.OK);
    }


//    @GetMapping("/details/{id}")
//    public ResponseEntity<?> queryWeatherByCity(@PathVariable int id) {
//        return new ResponseEntity<Map>(weatherService.findCityNameById(id), HttpStatus.OK);
//    }
    @Operation(summary = "Get port info for the weather service")
    @GetMapping("/details/port")
    public ResponseEntity<?> queryWeatherByCity() {
        int port = webServerAppCtxt.getWebServer().getPort();
        return new ResponseEntity<>("weather service running on port: " + port, HttpStatus.OK);
    }
}
