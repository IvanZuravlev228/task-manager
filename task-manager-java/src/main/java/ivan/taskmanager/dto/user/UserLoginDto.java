package ivan.taskmanager.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginDto {
    @Email
    private String email;
    @NotNull
    private String password;
}
