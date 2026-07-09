package com.ems.enrollment.controller;

import com.ems.common.response.ApiResponse;
import com.ems.enrollment.dto.request.CreateEnrollmentRequest;
import com.ems.enrollment.dto.response.EnrollmentResponse;
import com.ems.enrollment.producer.EnrollmentProducer;
import com.ems.enrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final EnrollmentProducer enrollmentProducer;

    @PostMapping("/enrollments")
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody CreateEnrollmentRequest request) {
        enrollmentProducer.sendEnrollmentRequest(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.success("Enrollment request accepted successfully"));
    }

    @GetMapping("/enrollments")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> findAll() {
        List<EnrollmentResponse> response = enrollmentService.findAll();
        return ResponseEntity.ok(ApiResponse.success("Enrollments retrieved successfully", response));
    }

    @GetMapping("/enrollments/{id}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> findById(@PathVariable Long id) {
        EnrollmentResponse response = enrollmentService.findById(id);
        return ResponseEntity.ok(ApiResponse.success("Enrollment retrieved successfully", response));
    }

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> findByStudentId(@PathVariable Long studentId) {
        List<EnrollmentResponse> response = enrollmentService.findByStudentId(studentId);
        return ResponseEntity.ok(ApiResponse.success("Student courses retrieved successfully", response));
    }
}
