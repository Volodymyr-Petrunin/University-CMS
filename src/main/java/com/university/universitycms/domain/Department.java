package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "departments")
@Getter
@Setter
public class Department {
    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dep_seq")
    @SequenceGenerator(name = "dep_seq", sequenceName = "dep_seq", allocationSize = 10)
    private Long id;
    @Column(name = "department_name")
    private String name;
    @OneToMany
    private List<Course> courses;

    public void addCourse(Course courses) {
        this.courses.add(courses);
    }
}
