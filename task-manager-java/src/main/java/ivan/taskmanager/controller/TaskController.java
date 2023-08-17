package ivan.taskmanager.controller;

import ivan.taskmanager.dto.task.TaskRequestDto;
import ivan.taskmanager.dto.task.TaskResponseDto;
import ivan.taskmanager.model.Task;
import ivan.taskmanager.service.TaskService;
import ivan.taskmanager.service.UserService;
import ivan.taskmanager.service.mapper.RequestResponseMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    private UserService userService;
    private RequestResponseMapper<TaskRequestDto, TaskResponseDto, Task> taskMapper;

    @GetMapping
    public List<TaskResponseDto> getAll() {
        return taskService.getAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public TaskResponseDto create(@RequestBody TaskRequestDto task,
                                  Authentication authentication) {
        task.setOwnerEmail(authentication.getName());
        return taskMapper.toDto(taskService.create(taskMapper.toModel(task)));
    }

    @GetMapping("/me")
    public List<TaskResponseDto> getAllByUserId(Authentication authentication) {
        return taskService.getAllByUserId(userService.getByEmail(authentication.getName()).getId())
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TaskResponseDto getById(@PathVariable Long id) {
        return taskMapper.toDto(taskService.getById(id));
    }

    @PutMapping("/{id}")
    public TaskResponseDto update(@PathVariable Long id,
                                  @RequestBody TaskRequestDto task,
                                  Authentication authentication) {
        return taskMapper.toDto(taskService.update(
                authentication.getName(), id, taskMapper.toModel(task)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }
}
