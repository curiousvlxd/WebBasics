package step.learning.services;

import step.learning.entities.User;

public interface EmailService {
    public boolean send(User to, String subject, String body);
}

