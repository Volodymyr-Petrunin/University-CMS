package com.university.universitycms.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private String name;
    private String audience;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long courseId;
    private Long groupId;
    private Long teacherId;
}
