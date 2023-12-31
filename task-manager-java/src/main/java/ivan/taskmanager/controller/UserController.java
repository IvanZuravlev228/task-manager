package ivan.taskmanager.controller;

import ivan.taskmanager.dto.user.UserLoginDto;
import ivan.taskmanager.dto.user.UserRequestDto;
import ivan.taskmanager.dto.user.UserResponseDto;
import ivan.taskmanager.model.User;
import ivan.taskmanager.security.jwt.JwtTokenProvider;
import ivan.taskmanager.service.AuthenticationService;
import ivan.taskmanager.service.UserService;
import ivan.taskmanager.service.mapper.RequestResponseMapper;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private RequestResponseMapper<UserRequestDto, UserResponseDto, User> userMapper;
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto user) {
        return new ResponseEntity<>(userMapper.toDto(
                authenticationService.register(userMapper.toModel(user))), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto)
            throws RuntimeException {
        User user = authenticationService.login(
                    userLoginDto.getEmail(), userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe(Authentication authentication) {
        return new ResponseEntity<>(userMapper.toDto(
                userService.getByEmail(authentication.getName())), HttpStatus.OK);
    }
}
