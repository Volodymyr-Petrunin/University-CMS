package com.university.universitycms.repository;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findLessonByDayOfWeekAndGroupOrderByStartTimeAsc(DayOfWeek dayOfWeek, Group group);
    List<Lesson> findLessonsByDayOfWeekAndCourseInOrderByStartTimeAsc(DayOfWeek dayOfWeek, Set<Course> courses);
    List<Lesson> findLessonsByGroupOrderByDayOfWeekAscStartTimeAsc(Group group);
    List<Lesson> findLessonsByCourseInOrderByDayOfWeekAscStartTimeAsc(Set<Course> courses);
    void deleteAllByCourse(Course course);
}
