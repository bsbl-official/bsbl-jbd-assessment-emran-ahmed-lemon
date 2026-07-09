package com.ems.enrollment.entity;

import com.ems.common.base.BaseEntity;
import com.ems.course.entity.Course;
import com.ems.student.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"student_id", "course_id", "semester"},
                name = "uk_enrollment_student_course_semester"
        ))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String semester;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;
}
