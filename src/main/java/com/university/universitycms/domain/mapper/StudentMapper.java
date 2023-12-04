package com.university.universitycms.domain.mapper;

import com.university.universitycms.domain.Student;
import com.university.universitycms.domain.dto.StudentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO studentToStudentDTO(Student student);
    Student studentDTOToStudent(StudentDTO studentDTO);
}
