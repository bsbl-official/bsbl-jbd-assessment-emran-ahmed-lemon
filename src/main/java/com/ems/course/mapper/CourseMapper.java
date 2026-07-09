package com.ems.course.mapper;

import com.ems.course.dto.request.CreateCourseRequest;
import com.ems.course.dto.request.UpdateCourseRequest;
import com.ems.course.dto.response.CourseResponse;
import com.ems.course.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    Course toEntity(CreateCourseRequest request);

    CourseResponse toResponse(Course course);

    void updateEntity(UpdateCourseRequest request, @MappingTarget Course course);
}
