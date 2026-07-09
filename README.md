# Education Management System (EMS)

## Overview

The **Education Management System (EMS)** is a Spring Boot-based monolithic REST API designed to manage students, courses, and enrollments. The system follows enterprise development practices including JWT authentication, Redis caching, RabbitMQ messaging, global exception handling, Spring AOP logging, and layered architecture.

The system allows administrators to:

- Manage student information.
- Manage course information.
- Enroll students into courses.
- Authenticate users using JWT.
- Cache frequently requested data using Redis.
- Process enrollment requests asynchronously using RabbitMQ.

---

# Technology Stack

| Technology | Version |
|------------|----------|
| Java | 17 |
| Spring Boot | 3.x |
| Maven | Latest |
| PostgreSQL | Latest |
| Spring Data JPA | Hibernate |
| Spring Security | JWT Authentication |
| Redis | Caching |
| RabbitMQ | Message Queue |
| MapStruct | Entity Mapping |
| Lombok | Boilerplate Reduction |
| Spring AOP | Logging |
| Jakarta Validation | Request Validation |

---

# Project Architecture

```
education-management-system
│
├── auth
│
├── student
│
├── course
│
├── enrollment
│
├── common
│
└── EducationManagementApplication
```

The project follows a **feature-based monolithic architecture**.

Each module contains:

```
controller

service

repository

entity

dto
    ├── request
    └── response

mapper

exception
```

---

# Modules

## 1. Authentication Module

Responsible for user authentication.

Features

- User Registration
- User Login
- JWT Token Generation
- Password Encryption
- Stateless Authentication

Endpoints

```
POST /api/auth/register

POST /api/auth/login
```

All APIs except `/api/auth/**` require a valid JWT token.

---

## 2. Student Module

Responsible for student management.

### Student Entity

| Field | Type |
|---------|------|
| id | Long |
| studentId | String (Unique) |
| fullName | String |
| email | String |
| phone | String |
| department | String |
| admissionDate | LocalDate |

### Endpoints

```
POST    /api/students

GET     /api/students

GET     /api/students/{id}

PUT     /api/students/{id}

DELETE  /api/students/{id}
```

### Validation

- Student ID must be unique.
- Email must be valid.
- Email must be unique.
- Phone is required.
- Department is required.

---

## 3. Course Module

Responsible for course management.

### Course Entity

| Field | Type |
|---------|------|
| id | Long |
| courseCode | String (Unique) |
| courseName | String |
| credit | Integer |

### Endpoints

```
POST    /api/courses

GET     /api/courses

GET     /api/courses/{id}

PUT     /api/courses/{id}

DELETE  /api/courses/{id}
```

### Validation

- Course code must be unique.
- Credit must be greater than zero.

---

## 4. Enrollment Module

Responsible for student enrollment.

### Enrollment Entity

| Field | Type |
|---------|------|
| id | Long |
| student | ManyToOne |
| course | ManyToOne |
| semester | String |
| enrollmentDate | LocalDate |

### Endpoints

```
POST /api/enrollments

GET /api/enrollments

GET /api/enrollments/{id}

GET /api/students/{studentId}/courses
```

---

# Enrollment Workflow

Unlike the Student and Course modules, the Enrollment module uses **RabbitMQ** for asynchronous processing.

Workflow

```
Client

↓

POST /api/enrollments

↓

EnrollmentController

↓

EnrollmentProducer

↓

RabbitMQ Exchange

↓

RabbitMQ Queue

↓

EnrollmentConsumer

↓

EnrollmentService

↓

Database
```

The controller **does not directly save data**.

Instead, it publishes an enrollment request to RabbitMQ.

The consumer receives the message and performs:

1. Student validation
2. Course validation
3. Duplicate enrollment validation
4. Maximum course validation
5. Save enrollment

---

# Enrollment Business Rules

Before an enrollment is saved, the system validates:

### Student exists

If not:

```
StudentNotFoundException
```

---

### Course exists

If not:

```
CourseNotFoundException
```

---

### Duplicate Enrollment

A student cannot enroll in the same course during the same semester.

Unique combination:

```
Student

Course

Semester
```

---

### Maximum Courses

A student can enroll in **maximum five courses** in a semester.

---

### Enrollment Date

Automatically assigned:

```
LocalDate.now()
```

---

# JWT Authentication

Authentication Flow

```
User

↓

Login

↓

Username & Password

↓

AuthenticationManager

↓

JWT Generated

↓

Client Stores JWT

↓

Authorization Header

↓

JwtAuthenticationFilter

↓

Security Context
```

Authorization Header

```
Authorization: Bearer <JWT_TOKEN>
```

JWT expiration is configured inside

```
application.yml
```

---

# Redis Cache

Redis is used to cache frequently requested GET endpoints.

Cached APIs

```
GET /students

GET /students/{id}

GET /courses

GET /courses/{id}

GET /enrollments

GET /enrollments/{id}

GET /students/{studentId}/courses
```

Cache Flow

```
Request

↓

Redis

↓

Cache Found?

↓

YES

↓

Return Cache

↓

NO

↓

Database

↓

Save to Redis

↓

Return Response
```

Global cache expiration is configured inside

```
application.yml
```

Example

```yaml
spring:
  cache:
    redis:
      time-to-live: 10m
```

---

# RabbitMQ

RabbitMQ provides asynchronous enrollment processing.

Producer

```
EnrollmentProducer
```

Consumer

```
EnrollmentConsumer
```

Advantages

- Non-blocking request processing
- Decoupled architecture
- Better scalability
- Improved responsiveness

---

# Logging

Spring AOP is used to log every API request automatically.

Logged Information

- HTTP Method
- Request URL
- Request Body
- Response Status
- Execution Time

Example

```
========== API REQUEST ==========
POST /api/students

Request Body

Execution Time

Response Status

========== END ==========
```

Redis Logs

```
Retrieved students from Redis.
```

```
Retrieved students from Database.
```

RabbitMQ Logs

```
Enrollment request published.
```

```
Enrollment request consumed.
```

```
Enrollment completed successfully.
```

---

# Global Exception Handling

Implemented using

```
@RestControllerAdvice
```

Handled Exceptions

```
StudentNotFoundException

CourseNotFoundException

EnrollmentNotFoundException

DuplicateResourceException

BusinessException

ValidationException

InvalidRequestException

UnauthorizedException

AccessDeniedException

MethodArgumentNotValidException

ConstraintViolationException

HttpMessageNotReadableException

Exception
```

---

# API Response Format

Success

```json
{
    "success": true,
    "message": "Student created successfully",
    "data": {},
    "timestamp": "2026-07-09T12:30:00"
}
```

Error

```json
{
    "success": false,
    "message": "Student not found",
    "errorCode": "STUDENT_NOT_FOUND",
    "timestamp": "2026-07-09T12:30:00"
}
```

---

# Database Design

## Student

```
id

student_id (Unique)

full_name

email (Unique)

phone

department

admission_date
```

---

## Course

```
id

course_code (Unique)

course_name

credit
```

---

## Enrollment

```
id

student_id (FK)

course_id (FK)

semester

enrollment_date
```

Unique Constraint

```
(student_id, course_id, semester)
```

---

# Validation Rules

Student

- Student ID is unique.
- Email is unique.
- Email format is validated.
- Phone is required.

Course

- Course code is unique.
- Credit > 0.

Enrollment

- Student must exist.
- Course must exist.
- Maximum five courses per semester.
- Duplicate enrollment not allowed.

---

# Security

The project uses Spring Security with JWT.

Features

- Stateless authentication
- Password encryption using BCrypt
- JWT authentication filter
- Protected REST APIs
- Role-based foundation for future expansion

---

# Development Workflow

```
Create Student

↓

Create Course

↓

Register User

↓

Login

↓

Receive JWT

↓

Call Protected APIs

↓

Retrieve Students/Courses

↓

Redis Cache

↓

Create Enrollment

↓

RabbitMQ

↓

Consumer

↓

Business Validation

↓

Database

↓

Retrieve Enrollment
```

---

# Future Improvements

- Role-Based Access Control (Admin, Teacher, Student)
- Refresh Token Support
- Swagger/OpenAPI Documentation
- Docker Compose
- Flyway Database Migration
- Soft Delete
- Pagination & Sorting
- Search APIs
- Unit Testing
- Integration Testing
- Monitoring with Spring Boot Actuator
- CI/CD Pipeline
- Email Notifications
- Audit Logging
- File Upload for Student Profile Photos

---

# Conclusion

The Education Management System demonstrates a clean, layered Spring Boot monolithic architecture with enterprise features. By combining JWT authentication, Redis caching, RabbitMQ asynchronous messaging, Spring AOP logging, centralized exception handling, and RESTful API design, the application provides a scalable and maintainable foundation for managing students, courses, and enrollments while following modern backend development best practices.