
# Weather Microservices Project

This project is a Spring Cloud-based distributed microservices system simulating a weather information platform. It includes service registration, configuration management, API gateway, weather data services, and centralized log monitoring with Splunk.

## 🧩 Project Structure

```plaintext
weather-master/
├── config                      # Spring Cloud Config Server
├── discovery                   # Eureka Discovery Server
├── gateway                     # API Gateway (Spring Cloud Gateway)
├── search                      # Composite Service - combines other services
├── student-management-service  # Student data service
├── details                     # City/Weather details service
├── properties                  # Centralized configuration files (used by Config Server)
````

## 🛠️ Tech Stack

* Java 11
* Spring Boot 2.1.7
* Spring Cloud Greenwich SR2
* Eureka (Service Discovery)
* Spring Cloud Config
* Spring Cloud Gateway
* Logback (`logback-spring.xml`)
* Splunk Universal Forwarder + Splunk Enterprise
* Maven

## 📄 Features

* Centralized configuration via Spring Cloud Config
* Service discovery via Eureka
* Gateway routing with Spring Cloud Gateway
* Service-to-service calls using `RestTemplate`, Ribbon, and `CompletableFuture`
* Hystrix circuit breaker integration
* Logback-based file logging per service
* Splunk log aggregation and search
* Swagger UI API documentation

## ▶️ How to Run

### 1. Start infrastructure services (in order):

```bash
cd config && ./mvnw spring-boot:run
cd discovery && ./mvnw spring-boot:run
cd gateway && ./mvnw spring-boot:run
```

### 2. Start application services:

```bash
cd search && ./mvnw spring-boot:run
cd student-management-service && ./mvnw spring-boot:run
cd details && ./mvnw spring-boot:run
```

> ✅ Tip: Make sure IntelliJ or terminal's working directory is set correctly so logs are generated in `/logs/*.log`.

## 📦 Logging & Splunk Integration

Each microservice logs to its own file using `logback-spring.xml`.

Example log files:

```
search/logs/search.log
student-management-service/logs/student.log
config/logs/config.log
discovery/logs/discovery.log
details/logs/details.log
gateway/logs/gateway.log
```

The **Splunk Universal Forwarder** monitors these files and forwards data to **Splunk Enterprise** on `localhost:9997`.

### 🔍 Example Splunk search query:

```spl
source="*/logs/*.log"
```

> View results at: [http://localhost:8000](http://localhost:8000) → Search & Reporting

## 📸 Recommended Screenshots for Submission

* Splunk log search results (search.log, student.log, etc.)
* Swagger UI (`http://localhost:<port>/swagger-ui.html`)
* IntelliJ console output with `logger.info(...)`
* Terminal logs directory with `.log` files present

## 🔐 Notes

* Do not run services until `config` and `discovery` are running
* If using IntelliJ, set working directory to each service folder
* All logs rotate daily, keep up to 7 days of history
* Default ports: config (8100), discovery (8761), gateway (8085), search (9001)

