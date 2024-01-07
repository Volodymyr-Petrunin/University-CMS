package com.university.universitycms.domain.mapper;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.domain.dto.LessonDTO;
import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LessonMapper {

    @Autowired
    private EntityManager manager;

    public abstract LessonDTO lessonToLessonDTO(Lesson lesson);

    public abstract Lesson lessonDTOToLesson(LessonDTO lessonDTO);

    @AfterMapping
    public void fetchEntities(@MappingTarget Lesson lesson, LessonDTO lessonDTO) {
        lesson.setCourse(manager.find(Course.class, lessonDTO.getCourseId()));
        lesson.setGroup(manager.find(Group.class, lessonDTO.getGroupId()));
        lesson.setTeacher(manager.find(Teacher.class, lessonDTO.getTeacherId()));
    }
}
