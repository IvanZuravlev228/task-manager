package ivan.taskmanager.service;

import ivan.taskmanager.model.User;

public interface AuthenticationService {
    User register(User user);

    User login(String login, String password) throws RuntimeException;
}
