package ivan.taskmanager.service;

import ivan.taskmanager.model.Squad;
import ivan.taskmanager.model.Task;
import java.util.List;

public interface SquadService {
    List<Squad> getAllSquadsByUserEmail(Long userId);

    Squad getById(Long squadId);

    Squad create(Squad squad);

    List<Task> getAllTasks(Long squadId);

    List<Task> addTask(Long squadId, Long taskId);

    void delete(Long squadId);
}
