# Education Management System

A Spring Boot monolithic application demonstrating enterprise-level architecture patterns.

## Features

- JWT Authentication (register, login, stateless sessions)
- Student CRUD with validation and caching
- Course CRUD with validation and caching
- Enrollment with async RabbitMQ processing
- Redis caching on all GET endpoints (10-minute TTL)
- Spring AOP request/response logging
- Global exception handling with consistent API responses
- MapStruct DTO mapping
- PostgreSQL persistence with JPA/Hibernate

## Tech Stack

Java 17, Spring Boot 3.2, PostgreSQL, Redis, RabbitMQ, JWT (jjwt), MapStruct, Lombok, Spring AOP

## Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- Redis 7+
- RabbitMQ 3.12+

## Setup

```bash
# 1. Create PostgreSQL database
psql -U postgres -c "CREATE DATABASE education_db;"

# 2. Update credentials in src/main/resources/application.yml if needed

# 3. Build
mvn clean install

# 4. Run
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

## API Documentation

See [API-DOCUMENTATION.md](API-DOCUMENTATION.md) for all endpoints with request/response examples.

## Architecture

Feature-based package structure with shared `common` module. Each feature (auth, student, course, enrollment) contains its own controller, service, repository, entity, DTOs, and mapper.
