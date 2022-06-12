package hr.tvz.klobucaric.hardwareapp.controller;
import hr.tvz.klobucaric.hardwareapp.command.LoginCommand;
import hr.tvz.klobucaric.hardwareapp.dto.LoginDto;
import hr.tvz.klobucaric.hardwareapp.command.RegistrationCommand;
import hr.tvz.klobucaric.hardwareapp.dto.UserDto;
import hr.tvz.klobucaric.hardwareapp.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Transactional
@RestController
@RequestMapping("/authentication")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginDto login(@Valid @RequestBody final LoginCommand command) {
        return authenticationService.login(command)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials"));
    }

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody final RegistrationCommand registrationCommand) {
        return authenticationService.register(registrationCommand);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/promote/{username}")
    public ResponseEntity promoteUser(@PathVariable final String username) {
        boolean resultofPromoting = authenticationService.promoteUser(username);
         if(resultofPromoting){
             return  ResponseEntity.ok().build();
         }else {
             return ResponseEntity.badRequest().build();
         }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/demotion/{username}")
    public ResponseEntity demoteUser(@PathVariable final String username) {
        boolean resultofDemotion = authenticationService.demoteUser(username);
        if(resultofDemotion){
            return  ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}
