package ivan.taskmanager.controller;

import ivan.taskmanager.dto.squad.SquadRequestDto;
import ivan.taskmanager.dto.squad.SquadResponseDto;
import ivan.taskmanager.dto.task.TaskRequestDto;
import ivan.taskmanager.dto.task.TaskResponseDto;
import ivan.taskmanager.model.Squad;
import ivan.taskmanager.model.Task;
import ivan.taskmanager.model.User;
import ivan.taskmanager.service.SquadService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final SquadService squadService;
    private final TaskService taskService;
    private final UserService userService;
    private RequestResponseMapper<SquadRequestDto, SquadResponseDto, Squad> squadMapper;
    private RequestResponseMapper<TaskRequestDto, TaskResponseDto, Task> taskMapper;

    @PostMapping("/add/squad")
    public ResponseEntity<SquadResponseDto> create(@RequestBody SquadRequestDto squad) {
        return new ResponseEntity<>(squadMapper.toDto(
                squadService.create(squadMapper.toModel(squad))), HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<List<TaskResponseDto>> addTask(@RequestParam Long squadId,
                                                         @RequestBody TaskRequestDto dto) {
        Task task = taskService.create(taskMapper.toModel(dto));
        return new ResponseEntity<>(squadService.addTask(squadId, task.getId()).stream()
                .map(t -> taskMapper.toDto(t))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/squad/{squadId}")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(@PathVariable Long squadId) {
        return new ResponseEntity<>(squadService.getAllTasks(squadId).stream()
                .map(t -> taskMapper.toDto(t))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/squads")
    public ResponseEntity<List<SquadResponseDto>> getAllSquadsByUser(
            Authentication authentication) {
        User user = userService.getByEmail(authentication.getName());
        return new ResponseEntity<>(squadService.getAllSquadsByUserEmail(user.getId()).stream()
                .map(s -> squadMapper.toDto(s))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(@PathVariable Long id,
                                                  @RequestBody TaskRequestDto task,
                                                  Authentication authentication) {
        return new ResponseEntity<>(taskMapper.toDto(taskService.update(
                authentication.getName(), id, taskMapper.toModel(task))), HttpStatus.OK);
    }

    @DeleteMapping("/squads/{squadId}")
    public ResponseEntity<Void> delete(@PathVariable Long squadId,
                       Authentication authentication) {
        Squad squad = squadService.getById(squadId);
        if (!squad.getOwner().getEmail().equals(authentication.getName())) {
            throw new RuntimeException("You can't delete this squad,"
                    + " because you aren't the owner");
        }
        squadService.delete(squadId);
        squad.getTasks().forEach(t -> taskService.delete(t.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/squad/{squadId}/task/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long squadId,
                                           @PathVariable Long taskId) {
        Squad squad = squadService.getById(squadId);
        Task taskToRemove = squad.getTasks().stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);
        if (taskToRemove != null) {
            squad.getTasks().remove(taskToRemove);
            squadService.create(squad);
            taskService.delete(taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new RuntimeException("Can't delete task by id: " + taskId
                    + " for squad by id: " + squadId);
        }
    }
}
