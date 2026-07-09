package com.ems.student.repository;

import com.ems.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentId(String studentId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
