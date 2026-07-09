package com.ems.course.mapper;

import com.ems.course.dto.request.CreateCourseRequest;
import com.ems.course.dto.request.UpdateCourseRequest;
import com.ems.course.dto.response.CourseResponse;
import com.ems.course.entity.Course;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T17:22:53+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public Course toEntity(CreateCourseRequest request) {
        if ( request == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.courseCode( request.getCourseCode() );
        course.courseName( request.getCourseName() );
        course.credit( request.getCredit() );

        return course.build();
    }

    @Override
    public CourseResponse toResponse(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponse.CourseResponseBuilder courseResponse = CourseResponse.builder();

        courseResponse.id( course.getId() );
        courseResponse.courseCode( course.getCourseCode() );
        courseResponse.courseName( course.getCourseName() );
        courseResponse.credit( course.getCredit() );
        courseResponse.createdAt( course.getCreatedAt() );
        courseResponse.updatedAt( course.getUpdatedAt() );

        return courseResponse.build();
    }

    @Override
    public void updateEntity(UpdateCourseRequest request, Course course) {
        if ( request == null ) {
            return;
        }

        course.setCourseName( request.getCourseName() );
        course.setCredit( request.getCredit() );
    }
}
