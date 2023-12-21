package com.university.universitycms.repository;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findLessonByDayOfWeekOrderByStartTimeAsc(DayOfWeek day);
    List<Lesson> findLessonByOrderByDayOfWeekAscStartTimeAsc();
    void deleteAllByCourse(Course course);
}
