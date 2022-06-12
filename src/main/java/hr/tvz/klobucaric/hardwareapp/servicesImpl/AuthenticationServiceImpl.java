package hr.tvz.klobucaric.hardwareapp.servicesImpl;


import hr.tvz.klobucaric.hardwareapp.command.LoginCommand;
import hr.tvz.klobucaric.hardwareapp.domain.User;
import hr.tvz.klobucaric.hardwareapp.dto.LoginDto;
import hr.tvz.klobucaric.hardwareapp.command.RegistrationCommand;
import hr.tvz.klobucaric.hardwareapp.dto.UserDto;
import hr.tvz.klobucaric.hardwareapp.repository.RoleRepository;
import hr.tvz.klobucaric.hardwareapp.repository.UserRepository;
import hr.tvz.klobucaric.hardwareapp.services.JwtService;
import hr.tvz.klobucaric.hardwareapp.services.AuthenticationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public AuthenticationServiceImpl(JwtService jwtService, UserRepository userRepository, RoleRepository roleRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<LoginDto> login(LoginCommand command) {
        Optional<User> user = userRepository.findByUsername(command.getUsername());

        if (user.isEmpty() || !isMatchingPassword(command.getPassword(), user.get().getPassword())) {
            return Optional.empty();
        }
        return Optional.of(
                new LoginDto(jwtService.createJwt(user.get()))
        );
    }

    @Override
    public UserDto register(RegistrationCommand registrationCommand) {

        User user = new User();
        user.setFirstName(registrationCommand.getFirstName());
        user.setLastName(registrationCommand.getLastName());
        user.setEmail(registrationCommand.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registrationCommand.getPassword()));
        user.setAuthorities(new HashSet<>(Arrays.asList(roleRepository.findAuthorityByName("ROLE_USER"))));
        user.setUsername(registrationCommand.getUsername());

        return mapUserToDTO(userRepository.save(user));
    }

    @Override
    public boolean promoteUser(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        if(user.isEmpty()) return false;
        else{
            String userAuthority = user.get().getAuthorities().stream().findFirst().get().getName();
            switch (userAuthority) {
                case "ROLE_USER":
                    user.get().setAuthorities(new HashSet<>(Arrays.asList(roleRepository.findAuthorityByName("ROLE_STAFF"))));
                    break;
                default:
                    return false;
            }
            return userRepository.save(user.get()) != null;
        }
    }
    //TODO NACI NACIN DA SE PREKINE SESIJA I LOGOUTA
    @Override
    public boolean demoteUser(String userName) {

        Optional<User> user = userRepository.findByUsername(userName);
        if(user.isEmpty()) return false;
        else{
            String userAuthority = user.get().getAuthorities().stream().findFirst().get().getName();
            switch (userAuthority) {
                case "ROLE_STAFF":
                    user.get().setAuthorities(new HashSet<>(Arrays.asList(roleRepository.findAuthorityByName("ROLE_USER"))));
                    break;
                default:
                    return false;
            }
            return userRepository.save(user.get()) != null;
        }

    }

    private boolean isMatchingPassword(String rawPassword, String encryptedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword,encryptedPassword);
    }

    private UserDto mapUserToDTO(User user){
        return new UserDto(user.getFirstName(),user.getLastName(),user.getEmail(),user.getUsername());
    }



}
