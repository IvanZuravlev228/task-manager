package ivan.taskmanager.service.impl;

import ivan.taskmanager.model.User;
import ivan.taskmanager.repository.UserRepository;
import ivan.taskmanager.service.AuthenticationService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(String login, String password) throws RuntimeException {
        User user = userRepository.getUserByEmail(login).orElseThrow(() ->
                new NoSuchElementException("Can't find user by login: " + login));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect username or password!!!");
        }
        return user;
    }
}
