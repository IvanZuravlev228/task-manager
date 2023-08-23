package ivan.taskmanager.service.mapper.impl;

import ivan.taskmanager.dto.squad.SquadRequestDto;
import ivan.taskmanager.dto.squad.SquadResponseDto;
import ivan.taskmanager.model.Squad;
import ivan.taskmanager.model.Task;
import ivan.taskmanager.service.UserService;
import ivan.taskmanager.service.mapper.RequestResponseMapper;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SquadMapper implements
        RequestResponseMapper<SquadRequestDto, SquadResponseDto, Squad> {
    private final UserService userService;

    @Override
    public Squad toModel(SquadRequestDto dto) {
        Squad model = new Squad();
        model.setOwner(userService.getByEmail(dto.getOwnerEmail()));
        model.setTitle(dto.getTitle());
        model.setTasks(Collections.emptyList());
        return model;
    }

    @Override
    public SquadResponseDto toDto(Squad model) {
        SquadResponseDto dto = new SquadResponseDto();
        dto.setId(model.getId());
        dto.setOwnerEmail(model.getOwner().getEmail());
        dto.setTitle(model.getTitle());
        dto.setTasksId(model.getTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}
