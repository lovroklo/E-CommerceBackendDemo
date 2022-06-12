package hr.tvz.klobucaric.hardwareapp.dto;


public class LoginDto {

    private final String jwt;

    public LoginDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
