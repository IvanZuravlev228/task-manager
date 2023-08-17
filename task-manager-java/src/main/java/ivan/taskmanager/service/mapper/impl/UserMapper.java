package ivan.taskmanager.service.mapper.impl;

import ivan.taskmanager.dto.user.UserRequestDto;
import ivan.taskmanager.dto.user.UserResponseDto;
import ivan.taskmanager.model.User;
import ivan.taskmanager.service.mapper.RequestResponseMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements RequestResponseMapper<UserRequestDto, UserResponseDto, User> {
    @Override
    public User toModel(UserRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        return user;
    }

    @Override
    public UserResponseDto toDto(User model) {
        UserResponseDto dto = new UserResponseDto();
        dto.setEmail(model.getEmail());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setId(model.getId());
        return dto;
    }
}
