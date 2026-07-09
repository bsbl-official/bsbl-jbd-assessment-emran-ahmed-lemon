package com.ems.student.controller;

import com.ems.common.response.ApiResponse;
import com.ems.student.dto.request.CreateStudentRequest;
import com.ems.student.dto.request.UpdateStudentRequest;
import com.ems.student.dto.response.StudentResponse;
import com.ems.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> create(@Valid @RequestBody CreateStudentRequest request) {
        StudentResponse response = studentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> findAll() {
        List<StudentResponse> response = studentService.findAll();
        return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> findById(@PathVariable Long id) {
        StudentResponse response = studentService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("Student retrieved successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStudentRequest request) {
        StudentResponse response = studentService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Student updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
    }
}
