package com.ems.enrollment.repository;

import com.ems.course.entity.Course;
import com.ems.enrollment.entity.Enrollment;
import com.ems.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndCourseAndSemester(
            Student student,
            Course course,
            String semester
    );

    long countByStudentAndSemester(
            Student student,
            String semester
    );

    List<Enrollment> findByStudent(
            Student student
    );
}