package ivan.taskmanager.service.mapper.impl;

import ivan.taskmanager.dto.task.TaskRequestDto;
import ivan.taskmanager.dto.task.TaskResponseDto;
import ivan.taskmanager.model.Task;
import ivan.taskmanager.service.UserService;
import ivan.taskmanager.service.mapper.RequestResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMapper implements RequestResponseMapper<TaskRequestDto, TaskResponseDto, Task> {
    private UserService userService;

    @Override
    public Task toModel(TaskRequestDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(Task.Priority.valueOf(dto.getPriority()));
        task.setStatus(Task.Status.valueOf(dto.getStatus()));
        task.setStart(dto.getStart());
        task.setFinish(dto.getFinish());
        task.setOwner(userService.getByEmail(dto.getOwnerEmail()));
        return task;
    }

    @Override
    public TaskResponseDto toDto(Task model) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setTitle(model.getTitle());
        dto.setDescription(model.getDescription());
        dto.setPriority(model.getPriority().name());
        dto.setStatus(model.getStatus().name());
        dto.setStart(model.getStart());
        dto.setFinish(model.getFinish());
        dto.setOwnerEmail(model.getOwner().getEmail());
        dto.setId(model.getId());
        return dto;
    }
}
