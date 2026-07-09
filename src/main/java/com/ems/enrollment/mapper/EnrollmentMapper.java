package com.ems.enrollment.mapper;

import com.ems.enrollment.dto.response.EnrollmentResponse;
import com.ems.enrollment.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.fullName", target = "studentName")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.courseName", target = "courseName")
    @Mapping(source = "course.courseCode", target = "courseCode")
    EnrollmentResponse toResponse(Enrollment enrollment);
}
