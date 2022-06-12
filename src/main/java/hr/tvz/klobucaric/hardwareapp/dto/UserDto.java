package hr.tvz.klobucaric.hardwareapp.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"email","username"})
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;

    public UserDto(String firstName, String lastName, String email, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }

}
