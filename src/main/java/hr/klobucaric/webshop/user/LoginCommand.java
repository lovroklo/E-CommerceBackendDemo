package hr.klobucaric.webshop.user;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginCommand {

    @NotBlank(message = "Email must not be empty")
    @Email
    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;

}
