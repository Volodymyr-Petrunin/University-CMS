package com.university.universitycms.repositories;

import com.university.universitycms.domain.Lesson;
import lombok.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Override
    @NonNull
    @Query("SELECT l FROM Lesson l ORDER BY l.id ASC")
    List<Lesson> findAll();
    List<Lesson> findLessonByDayOfWeekOrderByStartTimeAsc(DayOfWeek day);
    List<Lesson> findLessonByOrderByDayOfWeekAscStartTimeAsc();
}
