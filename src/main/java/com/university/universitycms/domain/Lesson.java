package com.university.universitycms.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;

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
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    @OneToOne
    private Course course;
    @OneToOne
    private Group group;
}
