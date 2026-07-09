package com.ems.student.mapper;

import com.ems.student.dto.request.CreateStudentRequest;
import com.ems.student.dto.request.UpdateStudentRequest;
import com.ems.student.dto.response.StudentResponse;
import com.ems.student.entity.Student;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T17:22:53+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student toEntity(CreateStudentRequest request) {
        if ( request == null ) {
            return null;
        }

        Student.StudentBuilder student = Student.builder();

        student.studentId( request.getStudentId() );
        student.fullName( request.getFullName() );
        student.email( request.getEmail() );
        student.phone( request.getPhone() );
        student.department( request.getDepartment() );
        student.admissionDate( request.getAdmissionDate() );

        return student.build();
    }

    @Override
    public StudentResponse toResponse(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponse.StudentResponseBuilder studentResponse = StudentResponse.builder();

        studentResponse.id( student.getId() );
        studentResponse.studentId( student.getStudentId() );
        studentResponse.fullName( student.getFullName() );
        studentResponse.email( student.getEmail() );
        studentResponse.phone( student.getPhone() );
        studentResponse.department( student.getDepartment() );
        studentResponse.admissionDate( student.getAdmissionDate() );
        studentResponse.createdAt( student.getCreatedAt() );
        studentResponse.updatedAt( student.getUpdatedAt() );

        return studentResponse.build();
    }

    @Override
    public void updateEntity(UpdateStudentRequest request, Student student) {
        if ( request == null ) {
            return;
        }

        student.setFullName( request.getFullName() );
        student.setEmail( request.getEmail() );
        student.setPhone( request.getPhone() );
        student.setDepartment( request.getDepartment() );
        student.setAdmissionDate( request.getAdmissionDate() );
    }
}
