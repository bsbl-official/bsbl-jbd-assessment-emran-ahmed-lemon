package com.ems.course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse implements Serializable {

    private Long id;
    private String courseCode;
    private String courseName;
    private Integer credit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
