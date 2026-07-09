package com.ems.course.service;

import com.ems.common.base.BaseService;
import com.ems.common.exception.DuplicateResourceException;
import com.ems.common.exception.ResourceNotFoundException;
import com.ems.course.dto.request.CreateCourseRequest;
import com.ems.course.dto.request.UpdateCourseRequest;
import com.ems.course.dto.response.CourseResponse;
import com.ems.course.entity.Course;
import com.ems.course.mapper.CourseMapper;
import com.ems.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService implements BaseService<CreateCourseRequest, CourseResponse, Long> {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "courses", allEntries = true)
    })
    public CourseResponse create(CreateCourseRequest request) {
        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new DuplicateResourceException("Course code already exists: " + request.getCourseCode(), "DUPLICATE_COURSE_CODE");
        }

        Course course = courseMapper.toEntity(request);
        Course saved = courseRepository.save(course);
        log.info("Course created with ID: {}", saved.getId());
        return courseMapper.toResponse(saved);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "courses", allEntries = true),
            @CacheEvict(value = "course", key = "#id")
    })
    public CourseResponse update(Long id, UpdateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id, "COURSE_NOT_FOUND"));

        courseMapper.updateEntity(request, course);
        Course updated = courseRepository.save(course);
        log.info("Course updated with ID: {}", updated.getId());
        return courseMapper.toResponse(updated);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "courses", allEntries = true),
            @CacheEvict(value = "course", key = "#id")
    })
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id, "COURSE_NOT_FOUND");
        }
        courseRepository.deleteById(id);
        log.info("Course deleted with ID: {}", id);
    }

    @Override
    @Cacheable(value = "course", key = "#id")
    public CourseResponse findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id, "COURSE_NOT_FOUND"));
        log.info("Retrieved course from Database with ID: {}", id);
        return courseMapper.toResponse(course);
    }

    @Override
    @Cacheable(value = "courses")
    public List<CourseResponse> findAll() {
        log.info("Retrieved courses from Database");
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    @Override
    public CourseResponse update(Long id, CreateCourseRequest request) {
        UpdateCourseRequest updateReq = new UpdateCourseRequest();
        updateReq.setCourseName(request.getCourseName());
        updateReq.setCredit(request.getCredit());
        return update(id, updateReq);
    }
}
