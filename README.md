```markdown
# Weather Microservices Project

This project is a Spring Cloud-based distributed microservices system simulating a weather information platform. It includes service registration, configuration management, API gateway, weather data services, and centralized log monitoring.

## 🧩 Project Structure

```

weather-master/
├── config                          # Spring Cloud Config Server
├── discovery                       # Eureka Discovery Server
├── gateway                         # API Gateway (Spring Cloud Gateway)
├── search                          # Composite Service - combines other services
├── student-management-service      # Student data service
├── details                         # City/Weather details service
├── properties                      # Centralized configuration files (used by Config Server)

````

## 🛠️ Tech Stack

- Java 11
- Spring Boot 2.1.7
- Spring Cloud Greenwich SR2
- Eureka (Service Discovery)
- Spring Cloud Config
- Spring Cloud Gateway
- Logback (Custom logging via `logback-spring.xml`)
- Splunk (Log aggregation and search)
- Maven

## 📄 Features

- Centralized config management via Spring Cloud Config Server
- Service discovery and registration via Eureka
- Gateway routing via Spring Cloud Gateway
- Microservices call each other using `RestTemplate` + Ribbon + CompletableFuture
- Hystrix circuit breaker for fault tolerance
- Logback-based file logging per service
- Splunk Universal Forwarder to send logs to local Splunk Enterprise
- API documentation via Swagger UI

## 🧪 How to Run

### 1. Start Support Services (in order):

```bash
cd config && ./mvnw spring-boot:run
cd discovery && ./mvnw spring-boot:run
cd gateway && ./mvnw spring-boot:run
````

### 2. Start Application Services:

```bash
cd search && ./mvnw spring-boot:run
cd student-management-service && ./mvnw spring-boot:run
cd details && ./mvnw spring-boot:run
```

> Make sure your working directory is set correctly if using IntelliJ (so logs will write to `/logs/*.log`).

## 📦 Logging & Splunk Integration

Each microservice writes logs to its own log file using `logback-spring.xml`.

Example log files:

* `search/logs/search.log`
* `student-management-service/logs/student.log`
* `config/logs/config.log`
* `discovery/logs/discovery.log`
* `details/logs/details.log`
* `gateway/logs/gateway.log`

A local **Splunk Universal Forwarder** is configured to monitor these files and send logs to local **Splunk Enterprise** running at `localhost:9997`.

### 🔍 Example Splunk Search

```spl
source="*search.log" OR source="*student.log"
```

## 📸 Recommended Screenshots for Submission

* ✅ Splunk Web showing logs from at least 2 services
* ✅ Swagger UI page (`http://localhost:<port>/swagger-ui.html`)
* ✅ IntelliJ console output showing `INFO` logs (from `logger.info`)
* ✅ Directory tree showing `/logs/*.log` files per service

## 📌 Notes

* Do not run the project until the config and discovery services are running
* Use `git clone` to download this project and `mvnw` to build and run
* Tested on macOS with Java 11 and Maven Wrapper

