package com.university.universitycms.repositories;

import com.university.universitycms.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByOrderByIdAsc();
    List<Lesson> findLessonByDayOfWeekOrderByStartTimeAsc(DayOfWeek day);
    List<Lesson> findLessonByOrderByDayOfWeekAscStartTimeAsc();
}
