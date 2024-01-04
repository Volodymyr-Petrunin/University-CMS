package com.university.universitycms.domain.mapper;

import com.university.universitycms.domain.Lesson;
import com.university.universitycms.domain.dto.LessonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDTO lessonToLessonDTO(Lesson lesson);
    Lesson lessonDTOToLesson(LessonDTO lessonDTO);
}
