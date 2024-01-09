package com.university.universitycms.repository;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findLessonByDayOfWeekAndGroupOrderByStartTimeAsc(DayOfWeek dayOfWeek, Group group);
    List<Lesson> findLessonsByDayOfWeekAndTeacherOrderByStartTimeAsc(DayOfWeek dayOfWeek, Teacher teacher);
    List<Lesson> findLessonsByGroupOrderByDayOfWeekAscStartTimeAsc(Group group);
    List<Lesson> findLessonsByTeacherOrderByDayOfWeekAscStartTimeAsc(Teacher teacher);
    List<Lesson> findLessonsByOrderByDayOfWeekAscStartTimeAsc();
    List<Lesson> findAllByAudienceAndStartTimeBetweenAndDayOfWeek(String audience, LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek);
    List<Lesson> findAllByGroupAndStartTimeBetweenAndDayOfWeek(Group group, LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek);
    List<Lesson> findAllByTeacherAndStartTimeBetweenAndDayOfWeek(Teacher teacher, LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek);
}
