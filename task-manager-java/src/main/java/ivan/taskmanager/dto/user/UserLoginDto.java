package ivan.taskmanager.dto.user;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;
}
