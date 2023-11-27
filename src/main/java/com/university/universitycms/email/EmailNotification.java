package com.university.universitycms.email;

public interface EmailNotification {
    void sendRegistrationConfirmation(String name, String email, char[] password);
}
