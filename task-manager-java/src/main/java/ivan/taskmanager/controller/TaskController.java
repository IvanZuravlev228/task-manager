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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TaskResponseDto>> getAll() {
        return new ResponseEntity<>(taskService.getAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> create(@RequestBody TaskRequestDto task,
                                  Authentication authentication) {
        task.setOwnerEmail(authentication.getName());
        return new ResponseEntity<>(taskMapper.toDto(taskService.create(taskMapper.toModel(task))),
                HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<List<TaskResponseDto>> getAllByUserId(Authentication authentication) {
        return new ResponseEntity<>(taskService.getAllByUserId(
                userService.getByEmail(authentication.getName()).getId()).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(taskMapper.toDto(taskService.getById(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable Long id,
                                  @RequestBody TaskRequestDto task,
                                  Authentication authentication) {
        return new ResponseEntity<>(taskMapper.toDto(taskService.update(
                authentication.getName(), id, taskMapper.toModel(task))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
