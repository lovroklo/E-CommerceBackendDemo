package hr.klobucaric.webshop.user;

import hr.klobucaric.webshop.role.RoleRepository;
import hr.klobucaric.webshop.security.JwtUtils;
import hr.klobucaric.webshop.security.SecurityUser;
import hr.klobucaric.webshop.shoppingCart.ShoppingCart;
import hr.klobucaric.webshop.shoppingCart.ShoppingCartRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ShoppingCartRepository shoppingCartRepository;

	@Override
	public UserDto register(RegistrationCommand command) throws UserAlreadyExistException {
		if(userRepository.existsByEmail(command.getEmail())) {
			throw new UserAlreadyExistException("User already exists for this email: " + command.getEmail());
		}
		User user = mapCommandToUser(command);
		encodePassword(user);
		user.setRoles(Stream.of(roleRepository.findByName("ROLE_USER")).collect(Collectors.toCollection(HashSet::new)));
		log.info("Saving user and shopping cart to database! User: "+ user.getEmail());
		user=userRepository.save(user);
		shoppingCartRepository.save(new ShoppingCart(user));
		return mapUserToUserDto(user);
	}

	@Override
	public ResponseCookie authenticate(LoginCommand command) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword())
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			ResponseCookie jwtCookie = jwtUtils.generateJwtCookie((SecurityUser) authentication.getPrincipal());
			log.info("Authenticating user and returning jwtCookie with token: " +jwtCookie.getDomain());
			return jwtCookie;
		} catch (BadCredentialsException e) {
			throw new ApiBadRequestException("Bad credentials, send correct ones!");
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong in user service! "+ e.getMessage());
		}
	}

	@Override
	public ResponseCookie logout() {
		return jwtUtils.getCleanJwtCookie();
	}



	private void encodePassword(User user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	}

	private UserDto mapUserToUserDto(User user){
		return new UserDto(user.getEmail(), user.getPhoneNumber());
	}

	private User mapCommandToUser(RegistrationCommand registrationCommand){
		return new User(
				registrationCommand.getEmail(),
				registrationCommand.getPhoneNumber(),
				registrationCommand.getPassword(),
				registrationCommand.getFirstName(),
				registrationCommand.getLastName()
		);
	}
}
