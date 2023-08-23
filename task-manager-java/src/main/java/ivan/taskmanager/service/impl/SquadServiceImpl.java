package ivan.taskmanager.service.impl;

import ivan.taskmanager.model.Squad;
import ivan.taskmanager.model.Task;
import ivan.taskmanager.repository.SquadRepository;
import ivan.taskmanager.service.SquadService;
import ivan.taskmanager.service.TaskService;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SquadServiceImpl implements SquadService {
    private final SquadRepository squadRepository;
    private final TaskService taskService;

    @Override
    public List<Squad> getAllSquadsByUserEmail(Long userId) {
        return squadRepository.findAllByTasksOwner(userId);
    }

    @Override
    public Squad getById(Long squadId) {
        return squadRepository.findById(squadId).orElseThrow(() ->
                new RuntimeException("Can't find squad by id: " + squadId));
    }

    @Override
    public Squad create(Squad squad) {
        return squadRepository.save(squad);
    }

    @Override
    public List<Task> getAllTasks(Long squadId) {
        List<Task> tasks = getById(squadId).getTasks();
        tasks.sort(Comparator.comparing(Task::getPriority, Collections.reverseOrder())
                .thenComparing(Task::getFinish));
        return tasks;
    }

    @Override
    public List<Task> addTask(Long squadId, Long taskId) {
        Squad squad = getById(squadId);
        squad.getTasks().add(taskService.getById(taskId));
        return create(squad).getTasks();
    }

    @Override
    public void delete(Long squadId) {
        squadRepository.deleteById(squadId);
    }
}
