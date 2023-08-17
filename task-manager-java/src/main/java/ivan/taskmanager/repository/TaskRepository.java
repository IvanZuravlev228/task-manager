package ivan.taskmanager.repository;

import ivan.taskmanager.model.Task;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOwnerId(Long id);

    List<Task> findByFinishGreaterThan(LocalDateTime currentDate);
}
