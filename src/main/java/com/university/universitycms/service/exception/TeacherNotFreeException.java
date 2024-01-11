package com.university.universitycms.service.exception;

public class TeacherNotFreeException extends RuntimeException {
    public TeacherNotFreeException(String teacherName, String teacherSurname) {
        super("Teacher: " + teacherName + " " + teacherSurname +  ", is not free at this time.");
    }
}
