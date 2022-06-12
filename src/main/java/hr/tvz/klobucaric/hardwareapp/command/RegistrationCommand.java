package hr.tvz.klobucaric.hardwareapp.command;
import hr.tvz.klobucaric.hardwareapp.validations.ValidPassword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationCommand {

    @NotBlank(message = "firstName must not be empty")
    private String firstName;

    @NotBlank(message = "lastName must not be empty")
    private String lastName;

    @Email(message = "email must be valid!")
    @NotBlank(message = "email must not be empty")
    private String email;

    @ValidPassword
    private String password;

    @NotBlank(message = "Username must not be empty")
    private String username;

    @Override
    public String toString() {
        return "RegistrationDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
