# Education Management System - API Documentation

Base URL: `http://localhost:8080/api`

All endpoints except `/api/auth/**` require JWT authentication.
Add the header: `Authorization: Bearer <JWT_TOKEN>`

---

## 1. Authentication Module

### 1.1 Register

```
POST /api/auth/register
```

**Request Body:**

```json
{
    "username": "admin",
    "password": "admin123",
    "fullName": "System Admin"
}
```

**Success Response (201):**

```json
{
    "success": true,
    "message": "Registration successful",
    "data": {
        "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6...",
        "tokenType": "Bearer",
        "expiresIn": 3600000
    },
    "timestamp": "2026-07-09T12:00:00"
}
```

**Error Response (409):**

```json
{
    "success": false,
    "message": "Username already exists",
    "errorCode": "DUPLICATE_USERNAME",
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 1.2 Login

```
POST /api/auth/login
```

**Request Body:**

```json
{
    "username": "admin",
    "password": "admin123"
}
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6...",
        "tokenType": "Bearer",
        "expiresIn": 3600000
    },
    "timestamp": "2026-07-09T12:00:00"
}
```

**Error Response (401):**

```json
{
    "success": false,
    "message": "Invalid username or password",
    "errorCode": "INVALID_CREDENTIALS",
    "timestamp": "2026-07-09T12:00:00"
}
```

---

## 2. Student Module

### 2.1 Create Student

```
POST /api/students
```

**Request Body:**

```json
{
    "studentId": "STU-001",
    "fullName": "Emran ahmed",
    "email": "john.doe@university.edu",
    "phone": "+01620829365",
    "department": "Computer Science",
    "admissionDate": "2026-01-15"
}
```

**Success Response (201):**

```json
{
    "success": true,
    "message": "Student created successfully",
    "data": {
        "id": 1,
        "studentId": "STU-001",
        "fullName": "John Doe",
        "email": "john.doe@university.edu",
        "phone": "+1-555-0101",
        "department": "Computer Science",
        "admissionDate": "2026-01-15",
        "createdAt": "2026-07-09T12:00:00",
        "updatedAt": "2026-07-09T12:00:00"
    },
    "timestamp": "2026-07-09T12:00:00"
}
```

**Error Response (409) - Duplicate Student ID:**

```json
{
    "success": false,
    "message": "Student ID already exists: STU-001",
    "errorCode": "DUPLICATE_STUDENT_ID",
    "timestamp": "2026-07-09T12:00:00"
}
```

**Error Response (400) - Validation:**

```json
{
    "success": false,
    "message": "email: Email must be valid, phone: Phone is required",
    "errorCode": "VALIDATION_ERROR",
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 2.2 Get All Students

```
GET /api/students
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Students retrieved successfully",
    "data": [
        {
            "id": 1,
            "studentId": "STU-001",
            "fullName": "John Doe",
            "email": "john.doe@university.edu",
            "phone": "+1-555-0101",
            "department": "Computer Science",
            "admissionDate": "2026-01-15",
            "createdAt": "2026-07-09T12:00:00",
            "updatedAt": "2026-07-09T12:00:00"
        }
    ],
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 2.3 Get Student by ID

```
GET /api/students/{id}
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Student retrieved successfully",
    "data": {
        "id": 1,
        "studentId": "STU-001",
        "fullName": "John Doe",
        "email": "john.doe@university.edu",
        "phone": "+1-555-0101",
        "department": "Computer Science",
        "admissionDate": "2026-01-15",
        "createdAt": "2026-07-09T12:00:00",
        "updatedAt": "2026-07-09T12:00:00"
    },
    "timestamp": "2026-07-09T12:00:00"
}
```

**Error Response (404):**

```json
{
    "success": false,
    "message": "Student not found with id: 99",
    "errorCode": "STUDENT_NOT_FOUND",
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 2.4 Update Student

```
PUT /api/students/{id}
```

**Request Body:**

```json
{
    "fullName": "John A. Doe",
    "email": "john.doe@university.edu",
    "phone": "+1-555-0102",
    "department": "Software Engineering",
    "admissionDate": "2026-01-15"
}
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Student updated successfully",
    "data": {
        "id": 1,
        "studentId": "STU-001",
        "fullName": "John A. Doe",
        "email": "john.doe@university.edu",
        "phone": "+1-555-0102",
        "department": "Software Engineering",
        "admissionDate": "2026-01-15",
        "createdAt": "2026-07-09T12:00:00",
        "updatedAt": "2026-07-09T12:05:00"
    },
    "timestamp": "2026-07-09T12:05:00"
}
```

---

### 2.5 Delete Student

```
DELETE /api/students/{id}
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Student deleted successfully",
    "timestamp": "2026-07-09T12:10:00"
}
```

---

## 3. Course Module

### 3.1 Create Course

```
POST /api/courses
```

**Request Body:**

```json
{
    "courseCode": "CS101",
    "courseName": "Introduction to Computer Science",
    "credit": 3
}
```

**Success Response (201):**

```json
{
    "success": true,
    "message": "Course created successfully",
    "data": {
        "id": 1,
        "courseCode": "CS101",
        "courseName": "Introduction to Computer Science",
        "credit": 3,
        "createdAt": "2026-07-09T12:00:00",
        "updatedAt": "2026-07-09T12:00:00"
    },
    "timestamp": "2026-07-09T12:00:00"
}
```

**Error Response (409) - Duplicate Course Code:**

```json
{
    "success": false,
    "message": "Course code already exists: CS101",
    "errorCode": "DUPLICATE_COURSE_CODE",
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 3.2 Get All Courses

```
GET /api/courses
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Courses retrieved successfully",
    "data": [
        {
            "id": 1,
            "courseCode": "CS101",
            "courseName": "Introduction to Computer Science",
            "credit": 3,
            "createdAt": "2026-07-09T12:00:00",
            "updatedAt": "2026-07-09T12:00:00"
        }
    ],
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 3.3 Get Course by ID

```
GET /api/courses/{id}
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Course retrieved successfully",
    "data": {
        "id": 1,
        "courseCode": "CS101",
        "courseName": "Introduction to Computer Science",
        "credit": 3,
        "createdAt": "2026-07-09T12:00:00",
        "updatedAt": "2026-07-09T12:00:00"
    },
    "timestamp": "2026-07-09T12:00:00"
}
```

**Error Response (404):**

```json
{
    "success": false,
    "message": "Course not found with id: 99",
    "errorCode": "COURSE_NOT_FOUND",
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 3.4 Update Course

```
PUT /api/courses/{id}
```

**Request Body:**

```json
{
    "courseName": "Intro to CS (Updated)",
    "credit": 4
}
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Course updated successfully",
    "data": {
        "id": 1,
        "courseCode": "CS101",
        "courseName": "Intro to CS (Updated)",
        "credit": 4,
        "createdAt": "2026-07-09T12:00:00",
        "updatedAt": "2026-07-09T12:05:00"
    },
    "timestamp": "2026-07-09T12:05:00"
}
```

---

### 3.5 Delete Course

```
DELETE /api/courses/{id}
```

**Success Response (200):**

```json
{
    "success": false,
    "message": "Course deleted successfully",
    "timestamp": "2026-07-09T12:10:00"
}
```

---

## 4. Enrollment Module

### 4.1 Create Enrollment (Async via RabbitMQ)

```
POST /api/enrollments
```

**Request Body:**

```json
{
  "studentId": "STU-001",
  "courseId": "CS101",
  "semester": "Fall 2026"
}
```

**Success Response (202 Accepted):**

```json
{
    "success": true,
    "message": "Enrollment request accepted successfully",
    "timestamp": "2026-07-09T12:00:00"
}
```

**Note:** The enrollment is processed asynchronously via RabbitMQ. The consumer
validates all business rules before persisting. Possible consumer-side errors
(logged, not returned to client):
- STUDENT_NOT_FOUND
- COURSE_NOT_FOUND
- DUPLICATE_ENROLLMENT
- MAX_COURSE_LIMIT_EXCEEDED

---

### 4.2 Get All Enrollments

```
GET /api/enrollments
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Enrollments retrieved successfully",
    "data": [
        {
            "id": 1,
            "studentId": 1,
            "studentName": "John Doe",
            "courseId": 1,
            "courseName": "Introduction to Computer Science",
            "courseCode": "CS101",
            "semester": "Fall 2026",
            "enrollmentDate": "2026-07-09",
            "createdAt": "2026-07-09T12:00:00"
        }
    ],
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 4.3 Get Enrollment by ID

```
GET /api/enrollments/{id}
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Enrollment retrieved successfully",
    "data": {
        "id": 1,
        "studentId": 1,
        "studentName": "John Doe",
        "courseId": 1,
        "courseName": "Introduction to Computer Science",
        "courseCode": "CS101",
        "semester": "Fall 2026",
        "enrollmentDate": "2026-07-09",
        "createdAt": "2026-07-09T12:00:00"
    },
    "timestamp": "2026-07-09T12:00:00"
}
```

**Error Response (404):**

```json
{
    "success": false,
    "message": "Enrollment not found with id: 99",
    "errorCode": "ENROLLMENT_NOT_FOUND",
    "timestamp": "2026-07-09T12:00:00"
}
```

---

### 4.4 Get Courses for a Student

```
GET /api/students/{studentId}/courses
```

**Success Response (200):**

```json
{
    "success": true,
    "message": "Student courses retrieved successfully",
    "data": [
        {
            "id": 1,
            "studentId": 1,
            "studentName": "John Doe",
            "courseId": 1,
            "courseName": "Introduction to Computer Science",
            "courseCode": "CS101",
            "semester": "Fall 2026",
            "enrollmentDate": "2026-07-09",
            "createdAt": "2026-07-09T12:00:00"
        },
        {
            "id": 2,
            "studentId": 1,
            "studentName": "John Doe",
            "courseId": 2,
            "courseName": "Data Structures",
            "courseCode": "CS201",
            "semester": "Fall 2026",
            "enrollmentDate": "2026-07-09",
            "createdAt": "2026-07-09T12:01:00"
        }
    ],
    "timestamp": "2026-07-09T12:02:00"
}
```

---

## API Summary Table

| Method | Endpoint                       | Auth Required | Description                        |
|--------|--------------------------------|---------------|------------------------------------|
| POST   | /api/auth/register             | No            | Register a new user                |
| POST   | /api/auth/login                | No            | Login and get JWT token            |
| POST   | /api/students                  | Yes           | Create a student                   |
| GET    | /api/students                  | Yes           | Get all students                   |
| GET    | /api/students/{id}             | Yes           | Get student by ID                  |
| PUT    | /api/students/{id}             | Yes           | Update student                     |
| DELETE | /api/students/{id}             | Yes           | Delete student                     |
| POST   | /api/courses                   | Yes           | Create a course                    |
| GET    | /api/courses                   | Yes           | Get all courses                    |
| GET    | /api/courses/{id}              | Yes           | Get course by ID                   |
| PUT    | /api/courses/{id}              | Yes           | Update course                      |
| DELETE | /api/courses/{id}              | Yes           | Delete course                      |
| POST   | /api/enrollments               | Yes           | Create enrollment (async)          |
| GET    | /api/enrollments               | Yes           | Get all enrollments                |
| GET    | /api/enrollments/{id}          | Yes           | Get enrollment by ID               |
| GET    | /api/students/{id}/courses     | Yes           | Get courses for a student          |

---

## Prerequisites

1. Java 17
2. PostgreSQL (create database `education_db`)
3. Redis (running on port 6379)
4. RabbitMQ (running on port 5672)

## Running the Application

```bash
# Create the database
psql -U postgres -c "CREATE DATABASE education_db;"

# Build and run
mvn clean install
mvn spring-boot:run
```

## Testing Flow

1. Register via `POST /api/auth/register`
2. Copy the `accessToken` from the response
3. Use it as `Authorization: Bearer <token>` for all subsequent requests
4. Create students, then courses, then enrollments
