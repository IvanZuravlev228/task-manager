package ivan.taskmanager.dto.task;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TaskRequestDto {
    private String title;
    private String description;
    private String priority;
    private String status;
    private LocalDateTime start;
    private LocalDateTime finish;
    private String ownerEmail;
}
