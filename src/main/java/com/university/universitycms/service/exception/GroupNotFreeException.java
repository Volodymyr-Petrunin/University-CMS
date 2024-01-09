package com.university.universitycms.service.exception;

public class GroupNotFreeException extends RuntimeException {
    public GroupNotFreeException(String groupName) {
        super("Group: " + groupName +  ", is not free at this time.");
    }
}
