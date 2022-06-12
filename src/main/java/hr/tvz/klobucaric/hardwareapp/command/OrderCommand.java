package hr.tvz.klobucaric.hardwareapp.command;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
public class OrderCommand {
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String city;
    @NotEmpty
    private String address;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String postCode;



}