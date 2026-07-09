package com.ems.enrollment.repository;

import com.ems.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseIdAndSemester(Long studentId, Long courseId, String semester);

    long countByStudentIdAndSemester(Long studentId, String semester);

    List<Enrollment> findByStudentId(Long studentId);
}
