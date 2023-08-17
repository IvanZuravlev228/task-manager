package ivan.taskmanager.service.impl;

import ivan.taskmanager.model.User;
import ivan.taskmanager.repository.UserRepository;
import ivan.taskmanager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email).orElseThrow(() -> {
            throw new RuntimeException("Can't find user by email: " + email);
        });
    }
}
