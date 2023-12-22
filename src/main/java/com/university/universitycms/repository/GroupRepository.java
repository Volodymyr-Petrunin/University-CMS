package com.university.universitycms.repository;

import com.university.universitycms.domain.Group;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Override
    @EntityGraph(value = "group-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Group> findById(Long aLong);
}
