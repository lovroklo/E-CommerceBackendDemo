package hr.klobucaric.webshop.web;
import hr.klobucaric.webshop.security.JwtUtils;
import hr.klobucaric.webshop.security.SecurityUser;
import hr.klobucaric.webshop.user.LoginCommand;
import hr.klobucaric.webshop.user.RegistrationCommand;
import hr.klobucaric.webshop.user.UserDto;
import hr.klobucaric.webshop.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginCommand command) {
        ResponseCookie jwtCookie = userService.authenticate(command);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(jwtCookie);

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(){
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, userService.logout().toString())
                .body("You have been signed out!");
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody final RegistrationCommand registrationCommand) {
        return new ResponseEntity<>(userService.register(registrationCommand), HttpStatus.CREATED);
    }


}
