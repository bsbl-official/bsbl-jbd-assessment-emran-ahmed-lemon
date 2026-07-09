package com.ems.student.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse implements Serializable {

    private Long id;
    private String studentId;
    private String fullName;
    private String email;
    private String phone;
    private String department;
    private LocalDate admissionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
