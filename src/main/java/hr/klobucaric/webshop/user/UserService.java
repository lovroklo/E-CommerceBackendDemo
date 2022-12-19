package hr.klobucaric.webshop.user;

import org.springframework.http.ResponseCookie;

public interface UserService {

	UserDto register(RegistrationCommand command);
	ResponseCookie authenticate(LoginCommand command);
	ResponseCookie logout();
}
