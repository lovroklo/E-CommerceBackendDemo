package hr.klobucaric.webshop.user;
import hr.klobucaric.webshop.utils.validations.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class RegistrationCommand {

    @Email(message = "email must be valid!")
    @NotBlank(message = "email must not be empty")
    private String email;

    @ValidPassword
    private String password;

    @NotBlank(message = "Phone number must not be empty")
    private String phoneNumber;

    @NotBlank(message = "First name can't be empty!")
    private String firstName;

    @NotBlank(message = "Last name can't be empty!")
    private String lastName;
}
