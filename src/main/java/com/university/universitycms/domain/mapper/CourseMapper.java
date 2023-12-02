package com.university.universitycms.domain.mapper;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.dto.CourseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO courseToCourseDTO(Course course);
    Course courseDTOToCourse(CourseDTO courseDTO);
}
