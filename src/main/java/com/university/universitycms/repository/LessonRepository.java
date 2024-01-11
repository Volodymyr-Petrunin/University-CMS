package com.university.universitycms.repository;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT l FROM Lesson l " +
            "WHERE (l.audience = :audience " +
                    "OR l.group = :group " +
                    "OR l.teacher = :teacher)" +
            "AND ((l.startTime BETWEEN :startTime AND :endTime) " +
                    "OR (l.endTime BETWEEN :startTime AND :endTime))" +
            "AND (l.dayOfWeek = :dayOfWeek)")
    List<Lesson> findAllByExistence(
            @Param("audience") String audience, @Param("group") Group group, @Param("teacher") Teacher teacher,
            @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime,
            @Param("dayOfWeek") DayOfWeek dayOfWeek
    );
}
