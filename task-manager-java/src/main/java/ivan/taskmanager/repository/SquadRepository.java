package ivan.taskmanager.repository;

import ivan.taskmanager.model.Squad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SquadRepository extends JpaRepository<Squad, Long> {
    @Query("SELECT DISTINCT s FROM Squad s "
            + "LEFT JOIN s.tasks t "
            + "WHERE s.owner.id = :userId OR t.owner.id = :userId")
    List<Squad> findAllByTasksOwner(Long userId);

}
