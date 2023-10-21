package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "lessons")
@Getter
@Setter
public class Lesson {
    @Id
    @Column(name = "lesson_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_seq")
    @SequenceGenerator(name = "lesson_seq", sequenceName = "lesson_seq", allocationSize = 100)
    private Long id;
    @Column(name = "lesson_name")
    private String name;
    @Column(name = "lesson_audience")
    private String audience;
    @Transient
    private DayOfWeek dayOfWeek;
    @Transient
    private LocalTime startTime;
    @Transient
    private LocalTime endTime;

    @OneToOne
    @JoinColumn(name = "lesson_course_id")
    private Course course;
    @OneToOne
    @JoinColumn(name = "lesson_group_id")
    private Group group;
    public Lesson() {

    }

    public Lesson(Long id, String name, String audience, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, Course course, Group group) {
        this.id = id;
        this.name = name;
        this.audience = audience;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.course = course;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) && Objects.equals(name, lesson.name) && Objects.equals(audience, lesson.audience) && dayOfWeek == lesson.dayOfWeek && Objects.equals(startTime, lesson.startTime) && Objects.equals(endTime, lesson.endTime) && Objects.equals(course, lesson.course) && Objects.equals(group, lesson.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, audience, dayOfWeek, startTime, endTime, course, group);
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", audience='" + audience + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", course=" + course +
                ", group=" + group +
                '}';
    }
}
