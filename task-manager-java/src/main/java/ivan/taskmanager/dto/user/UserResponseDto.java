package ivan.taskmanager.dto.user;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstname;
    private String lastname;
}
