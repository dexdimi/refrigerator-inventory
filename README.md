# Fridge Service

A Spring Boot microservice for managing refrigerator inventory (fridge items). This service provides a REST API with CRUD operations, filtering, and pagination. It uses DTOs to decouple the API representation from the internal domain model. The project is built with Gradle, and the application along with a MySQL database is containerized using Docker and Docker Compose.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Setup and Running](#setup-and-running)
    - [Build with Gradle](#build-with-gradle)
    - [Run with Docker Compose](#run-with-docker-compose)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Environment Variables](#environment-variables)
- [Project Structure](#project-structure)
- [Notes](#notes)

## Features

- **CRUD Operations:** Create, read, update, and delete fridge items.
- **Filtering and Pagination:** Supports filtering by fridge item name and category, with paginated responses.
- **DTOs & Mapping:** Uses Data Transfer Objects to isolate the API from internal domain models.
- **API Documentation:** Interactive API documentation generated with Springdoc OpenAPI (Swagger UI).
- **Containerized:** Both the application and MySQL database are containerized.
- **Actuator:** Has Health endpoint for some monitoring tool.
- **Logging:** Logback and Slf4J are combined to deliver logging.
- **Gradle Build:** Gradle is used for dependency management and building the project.

## Architecture

- **Spring Boot:** Implements the REST API and business logic.
- **Gradle:** Manages dependencies and builds a fat jar.
- **Docker:** Containerizes the application using a Dockerfile.
- **Docker Compose:** Orchestrates multiple containers (the application and MySQL).
- **MySQL:** Provides persistent storage for fridge item data; initialized via an SQL script on first run.
- **DTOs and Mapper:** Controllers exchange DTOs with clients, while the service layer converts between DTOs and domain entities using a dedicated mapper (implemented with Lombok’s builder or a mapping library).

## Prerequisites

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)
- Java (OpenJDK 23.0.2.)
- Gradle (or use the provided Gradle wrapper)

## Setup and Running

### Build with Gradle

From the project root, run:
```
./gradlew clean build
```
This compiles the code and produces the executable jar under build/libs/.

### Dockerfile
The Dockerfile (located in the project root) builds the application image:

```
FROM openjdk:23-jdk-slim

WORKDIR /app

ARG JAR_FILE=build/libs/fridge-service-1.0.0.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```
### docker-compose.yaml
The Docker Compose file (also in the project root) defines two services: MySQL and the application.

```
services:
  mysql:
    image: mysql:8.4
    container_name: fridge_mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: refrigerator_db
    ports:
      - "3307:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./mysql-init/refrigerator_db_schema.sql:/docker-entrypoint-initdb.d/refrigerator_db_schema.sql
    healthcheck:
      test: [ "CMD", "mysqladmin", "ping", "-h", "localhost" ]
      interval: 10s
      timeout: 5s
      retries: 5
  app:
    build: .
    image: fridge-service:latest
    container_name: fridge_service
    ports:
      - "8080:8080"
    volumes:
      - ./logs:/app/logs
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://fridge_mysql:3306/refrigerator_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      mysql:
        condition: service_healthy
volumes:
  mysql_data:
networks:
  default:
    external: false
```    
### Run with Docker Compose
From the project root, execute:
```
docker-compose up --build
```
This builds the application image, starts the MySQL container (which initializes the database using the refrigerator_db_schema.sql script), and then starts the Spring Boot application container.

## API Documentation
Once the application is running, view the API documentation at:

```
http://localhost:8080/swagger-ui/index.html
```
This interactive documentation is generated using Springdoc OpenAPI.

## Testing
### Unit Testing
Unit tests are written with JUnit 5 and Mockito.
To run tests, use:
```
./gradlew test
```

### Integration Testing
Integration tests use Spring Boot Test and MockMvc to verify endpoints.
The tests ensure that CRUD operations, filtering, and pagination behave as expected.

## Environment Variables
The following environment variables are set in the docker-compose file:
```
MYSQL_ROOT_PASSWORD: Password for the MySQL root user.
MYSQL_DATABASE: Name of the MySQL database (e.g., refrigerator_db).
SPRING_DATASOURCE_URL: JDBC URL for connecting to MySQL.
SPRING_DATASOURCE_USERNAME / SPRING_DATASOURCE_PASSWORD: Credentials for database access.
```

## Project Structure
```
fridge-service/

├── build.gradle
├── Dockerfile
├── docker-compose.yaml
├── mysql-init/
│   └── refrigerator_db_schema.sql     # SQL script to initialize the database schema and seed data
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/hylastix/fridgeservice/
│   │   │       ├── config/             # Config files for CORS, object mapping and Basic auth
│   │   │       ├── controller/         # REST controllers
│   │   │       ├── dto/                # Data Transfer Objects
│   │   │       ├── exceptions/         # Global and Custom exception handlers
│   │   │       ├── filter/             # Specifications for filtering
│   │   │       ├── model/              # Domain entities
│   │   │       ├── repository/         # JPA repositories
│   │   │       └── service/            # Business logic and service implementations
│   │   └── resources/
│   │       └── application.properties  # Application configuration
│   │       └── logback-spring.xml      # Logging configuration
│   └── test/
│       ├── java/                       
│       │   ├── integration/            # Integration tests
│       │   └── service/                # Unit tests
│       └── resources/
│           └── application-test.properties  # Application test configuration
└── README.md
```

## Notes

### DTOs and Mapping:
The project uses DTOs to decouple the API from internal domain models. Mapping is handled by dedicated mapper classes.

### Database Initialization:
The MySQL container automatically executes SQL scripts found in /docker-entrypoint-initdb.d (mounted from ./mysql-init).

### Security:
Basic authentication is enabled for. Adjust the credentials in your docker-compose file or application configuration as needed.

### Logging:
Logback and Slf4J are combined to deliver logging, log file is in folder:
```
logs/fridge-service.log
```
and could be tracked in the container with the command:
```
docker exec -it fridge_service tail -f /app/logs/fridge-service.log
```

### Actuator

Detect issues and understand performance. Health endpoint for some monitoring tool:
```
http://localhost:8080/actuator/health
```

### Further Improvements:
Considering to add API versioning, improve Security, monitoring for production readiness, connection to other services and then to add the Circuit Breaker.
