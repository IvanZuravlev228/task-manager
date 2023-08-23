package ivan.taskmanager.dto.squad;

import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SquadResponseDto {
    private Long id;
    private String title;
    private String ownerEmail;
    private List<Long> tasksId;
}
