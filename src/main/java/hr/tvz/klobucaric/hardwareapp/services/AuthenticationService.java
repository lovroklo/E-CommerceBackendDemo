package hr.tvz.klobucaric.hardwareapp.services;

import hr.tvz.klobucaric.hardwareapp.command.LoginCommand;
import hr.tvz.klobucaric.hardwareapp.dto.LoginDto;
import hr.tvz.klobucaric.hardwareapp.command.RegistrationCommand;
import hr.tvz.klobucaric.hardwareapp.dto.UserDto;


import java.util.Optional;

public interface AuthenticationService {

    Optional<LoginDto> login(LoginCommand command);

    UserDto register(RegistrationCommand registrationCommand);

    boolean promoteUser(String userName);

    boolean demoteUser(String userName);

}
