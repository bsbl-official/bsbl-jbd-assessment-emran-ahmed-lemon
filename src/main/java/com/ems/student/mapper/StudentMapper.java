package com.ems.student.mapper;

import com.ems.student.dto.request.CreateStudentRequest;
import com.ems.student.dto.request.UpdateStudentRequest;
import com.ems.student.dto.response.StudentResponse;
import com.ems.student.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(CreateStudentRequest request);

    StudentResponse toResponse(Student student);

    void updateEntity(UpdateStudentRequest request, @MappingTarget Student student);
}
