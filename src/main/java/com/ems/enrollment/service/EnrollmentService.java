package com.ems.enrollment.service;

import com.ems.common.exception.BusinessException;
import com.ems.common.exception.DuplicateResourceException;
import com.ems.common.exception.ResourceNotFoundException;
import com.ems.course.entity.Course;
import com.ems.course.repository.CourseRepository;
import com.ems.enrollment.dto.request.CreateEnrollmentRequest;
import com.ems.enrollment.dto.response.EnrollmentResponse;
import com.ems.enrollment.entity.Enrollment;
import com.ems.enrollment.mapper.EnrollmentMapper;
import com.ems.enrollment.repository.EnrollmentRepository;
import com.ems.student.entity.Student;
import com.ems.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {

    private static final int MAX_COURSES_PER_SEMESTER = 5;

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "enrollments", allEntries = true),
            @CacheEvict(value = "studentCourses", key = "#request.studentId")
    })
    public EnrollmentResponse create(CreateEnrollmentRequest request) {

        Student student = studentRepository.findByStudentId(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with Student ID: " + request.getStudentId(),
                        "STUDENT_NOT_FOUND"));

        Course course = courseRepository.findByCourseCode(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with Course Code: " + request.getCourseId(),
                        "COURSE_NOT_FOUND"));

        if (enrollmentRepository.existsByStudentAndCourseAndSemester(
                student,
                course,
                request.getSemester())) {

            throw new DuplicateResourceException(
                    "Student is already enrolled in this course for semester: "
                            + request.getSemester(),
                    "DUPLICATE_ENROLLMENT");
        }

        long enrolledCount = enrollmentRepository.countByStudentAndSemester(
                student,
                request.getSemester());

        if (enrolledCount >= MAX_COURSES_PER_SEMESTER) {
            throw new BusinessException(
                    "A student can enroll in a maximum of "
                            + MAX_COURSES_PER_SEMESTER
                            + " courses per semester.",
                    "MAX_COURSE_LIMIT_EXCEEDED");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .semester(request.getSemester())
                .enrollmentDate(LocalDate.now())
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);

        log.info(
                "Enrollment completed successfully. Student: {}, Course: {}, Semester: {}",
                student.getStudentId(),
                course.getCourseCode(),
                request.getSemester());

        return enrollmentMapper.toResponse(saved);
    }

    @Cacheable("enrollments")
    public List<EnrollmentResponse> findAll() {
        log.info("Retrieved enrollments from Database");

        return enrollmentRepository.findAll()
                .stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }

    @Cacheable(value = "enrollment", key = "#id")
    public EnrollmentResponse findById(Long id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment not found with id: " + id,
                        "ENROLLMENT_NOT_FOUND"));

        log.info("Retrieved enrollment from Database with ID: {}", id);

        return enrollmentMapper.toResponse(enrollment);
    }

    @Cacheable(value = "studentCourses", key = "#studentId")
    public List<EnrollmentResponse> findByStudentId(String studentId) {

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with Student ID: " + studentId,
                        "STUDENT_NOT_FOUND"));

        log.info("Retrieved courses for Student ID {} from Database", studentId);

        return enrollmentRepository.findByStudent(student)
                .stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }
}