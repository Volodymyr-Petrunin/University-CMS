package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "department_course",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    public Department() {

    }

    public Department(Long id, String name, Set<Course> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public void addCourse(Course courses) {
        this.courses.add(courses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(courses, that.courses) || Objects.equals(courses, null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, courses);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
