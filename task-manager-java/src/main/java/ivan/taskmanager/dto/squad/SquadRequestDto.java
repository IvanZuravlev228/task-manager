package ivan.taskmanager.dto.squad;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SquadRequestDto {
    private String title;
    private String ownerEmail;
}
