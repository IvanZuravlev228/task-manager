package ivan.taskmanager.service.impl;

import ivan.taskmanager.model.Task;
import ivan.taskmanager.repository.TaskRepository;
import ivan.taskmanager.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findByFinishGreaterThan(LocalDateTime.now());
    }

    @Override
    public List<Task> getAllByUserId(Long userId) {
        return taskRepository.findAllByOwnerId(userId);
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Can't find task by id: " + id);
        });
    }

    @Override
    public Task update(String updaterEmail, Long taskId, Task task) {
        Task taskById = getById(taskId);
        if (!updaterEmail.equals(taskById.getOwner().getEmail())) {
            throw new RuntimeException("Can't update task with id " + taskId
                    + " because this person is not the owner");
        }
        task.setId(taskById.getId());
        return taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
