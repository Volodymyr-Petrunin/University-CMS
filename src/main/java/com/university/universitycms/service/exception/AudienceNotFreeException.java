package com.university.universitycms.service.exception;

public class AudienceNotFreeException extends RuntimeException {
    public AudienceNotFreeException(String audienceName) {
        super("Audience: " + audienceName + ", is not free at this time.");
    }
}
