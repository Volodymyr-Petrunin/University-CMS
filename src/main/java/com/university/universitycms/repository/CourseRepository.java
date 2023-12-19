package com.university.universitycms.repository;

import com.university.universitycms.domain.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Override
    @EntityGraph(value = "course-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Course> findById(Long aLong);

    Set<Course> findAllByIdIn(List<Long> id);
}
