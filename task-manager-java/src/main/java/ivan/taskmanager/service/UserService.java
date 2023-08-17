package ivan.taskmanager.service;

import ivan.taskmanager.model.User;

public interface UserService {
    User create(User user);

    User getByEmail(String email);
}
