package ivan.taskmanager.service;

import ivan.taskmanager.model.Task;
import java.util.List;

public interface TaskService {
    Task create(Task task);

    List<Task> getAll();

    List<Task> getAllByUserId(Long userId);

    Task getById(Long id);

    Task update(String updaterEmail, Long taskId, Task task);

    void delete(Long id);
}
