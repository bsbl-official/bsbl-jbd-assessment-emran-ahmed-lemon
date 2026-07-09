package com.ems.enrollment.mapper;

import com.ems.course.entity.Course;
import com.ems.enrollment.dto.response.EnrollmentResponse;
import com.ems.enrollment.entity.Enrollment;
import com.ems.student.entity.Student;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T17:22:53+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class EnrollmentMapperImpl implements EnrollmentMapper {

    @Override
    public EnrollmentResponse toResponse(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }

        EnrollmentResponse.EnrollmentResponseBuilder enrollmentResponse = EnrollmentResponse.builder();

        enrollmentResponse.studentId( enrollmentStudentId( enrollment ) );
        enrollmentResponse.studentName( enrollmentStudentFullName( enrollment ) );
        enrollmentResponse.courseId( enrollmentCourseId( enrollment ) );
        enrollmentResponse.courseName( enrollmentCourseCourseName( enrollment ) );
        enrollmentResponse.courseCode( enrollmentCourseCourseCode( enrollment ) );
        enrollmentResponse.id( enrollment.getId() );
        enrollmentResponse.semester( enrollment.getSemester() );
        enrollmentResponse.enrollmentDate( enrollment.getEnrollmentDate() );
        enrollmentResponse.createdAt( enrollment.getCreatedAt() );

        return enrollmentResponse.build();
    }

    private Long enrollmentStudentId(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }
        Student student = enrollment.getStudent();
        if ( student == null ) {
            return null;
        }
        Long id = student.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String enrollmentStudentFullName(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }
        Student student = enrollment.getStudent();
        if ( student == null ) {
            return null;
        }
        String fullName = student.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }

    private Long enrollmentCourseId(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }
        Course course = enrollment.getCourse();
        if ( course == null ) {
            return null;
        }
        Long id = course.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String enrollmentCourseCourseName(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }
        Course course = enrollment.getCourse();
        if ( course == null ) {
            return null;
        }
        String courseName = course.getCourseName();
        if ( courseName == null ) {
            return null;
        }
        return courseName;
    }

    private String enrollmentCourseCourseCode(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }
        Course course = enrollment.getCourse();
        if ( course == null ) {
            return null;
        }
        String courseCode = course.getCourseCode();
        if ( courseCode == null ) {
            return null;
        }
        return courseCode;
    }
}
