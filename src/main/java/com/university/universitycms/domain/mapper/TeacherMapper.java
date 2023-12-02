package com.university.universitycms.domain.mapper;

import com.university.universitycms.domain.Teacher;
import com.university.universitycms.domain.dto.TeacherDTO;
import org.mapstruct.Mapper;

@Mapper(uses = CourseMapper.class,componentModel = "spring")
public interface TeacherMapper {
    TeacherDTO teacherToTeacherDTO(Teacher teacher);
    Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);
}
