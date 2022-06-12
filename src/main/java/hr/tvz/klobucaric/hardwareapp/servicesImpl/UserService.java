package hr.tvz.klobucaric.hardwareapp.servicesImpl;

import hr.tvz.klobucaric.hardwareapp.domain.User;
import hr.tvz.klobucaric.hardwareapp.repository.RoleRepository;
import hr.tvz.klobucaric.hardwareapp.repository.UserRepository;
import hr.tvz.klobucaric.hardwareapp.services.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;



    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
