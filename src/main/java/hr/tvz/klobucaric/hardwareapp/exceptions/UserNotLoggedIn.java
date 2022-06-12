package hr.tvz.klobucaric.hardwareapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotLoggedIn extends RuntimeException {
    public UserNotLoggedIn(String message) {
        super(message);
    }
}