package ivan.taskmanager.dto.user;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserRequestDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
}
