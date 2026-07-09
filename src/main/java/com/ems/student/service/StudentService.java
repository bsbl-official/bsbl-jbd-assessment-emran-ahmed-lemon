package com.ems.student.service;

import com.ems.common.base.BaseService;
import com.ems.common.exception.DuplicateResourceException;
import com.ems.common.exception.ResourceNotFoundException;
import com.ems.student.dto.request.CreateStudentRequest;
import com.ems.student.dto.request.UpdateStudentRequest;
import com.ems.student.dto.response.StudentResponse;
import com.ems.student.entity.Student;
import com.ems.student.mapper.StudentMapper;
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
public class StudentService implements BaseService<CreateStudentRequest, StudentResponse, Long> {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "students", allEntries = true)
    })
    public StudentResponse create(CreateStudentRequest request) {
        if (studentRepository.existsByStudentId(request.getStudentId())) {
            throw new DuplicateResourceException("Student ID already exists: " + request.getStudentId(), "DUPLICATE_STUDENT_ID");
        }
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail(), "DUPLICATE_EMAIL");
        }

        Student student = studentMapper.toEntity(request);
        if (student.getAdmissionDate() == null) {
            student.setAdmissionDate(LocalDate.now());
        }

        Student saved = studentRepository.save(student);
        log.info("Student created with ID: {}", saved.getId());
        return studentMapper.toResponse(saved);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "students", allEntries = true),
            @CacheEvict(value = "student", key = "#id")
    })
    public StudentResponse update(Long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id, "STUDENT_NOT_FOUND"));

        if (studentRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail(), "DUPLICATE_EMAIL");
        }

        studentMapper.updateEntity(request, student);
        Student updated = studentRepository.save(student);
        log.info("Student updated with ID: {}", updated.getId());
        return studentMapper.toResponse(updated);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "students", allEntries = true),
            @CacheEvict(value = "student", key = "#id")
    })
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id, "STUDENT_NOT_FOUND");
        }
        studentRepository.deleteById(id);
        log.info("Student deleted with ID: {}", id);
    }

    @Override
    @Cacheable(value = "student", key = "#id")
    public StudentResponse findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id, "STUDENT_NOT_FOUND"));
        log.info("Retrieved student from Database with ID: {}", id);
        return studentMapper.toResponse(student);
    }

    @Override
    @Cacheable(value = "students")
    public List<StudentResponse> findAll() {
        log.info("Retrieved students from Database");
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    // BaseService contract requires this signature; delegate to the typed update
    @Override
    public StudentResponse update(Long id, CreateStudentRequest request) {
        UpdateStudentRequest updateReq = new UpdateStudentRequest();
        updateReq.setFullName(request.getFullName());
        updateReq.setEmail(request.getEmail());
        updateReq.setPhone(request.getPhone());
        updateReq.setDepartment(request.getDepartment());
        updateReq.setAdmissionDate(request.getAdmissionDate());
        return update(id, updateReq);
    }
}
