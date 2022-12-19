package hr.klobucaric.webshop.address;

import hr.klobucaric.webshop.security.SecurityUtils;
import hr.klobucaric.webshop.user.User;
import hr.klobucaric.webshop.user.UserRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;



@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

	private final AddressRepository addressRepository;
	private final UserRepository userRepository;
	private final SecurityUtils securityUtils;
	//This creates address for logged-in user, in case user already has the same address then we throw exception,
	// in case user wants address to be default then we set it to default and remove the one from before that was default,
	// in case it's first address for user it's automatically set to default

	@Transactional
	@Override
	public AddressDto createAddress(AddressCommand addressCommand) {
		User currentUser = getCurrentUser();
		Long  existingAddressId = addressExists(addressCommand);
		Boolean isDefault = false;
		Boolean userContainsExistingAddress = existingAddressId!=null ?
				addressRepository.userContainsAddress(currentUser.getId(), existingAddressId) : false;
		if(userContainsExistingAddress){
			throw new ApiBadRequestException("Logged in user already has this address!");
		} else if(currentUser.getUserAddresses().isEmpty() || addressCommand.getIsDefault()){
			isDefault=true;
			addressRepository.removeDefaultAddress(currentUser.getId());
		}
		Address address = mapCommandToAddress(addressCommand);
		address.addUserAddress(currentUser, isDefault);
		return mapAddressToDto(addressRepository.save(address));
	}

	@Override
	public void setDefaultAddress(Long id) {
		if(!addressRepository.addressExists(id)){
			throw new ApiNotFoundException("There is no address in database with id: "+ id);
		}
		User currentUser = getCurrentUser();

		if(addressRepository.userContainsAddressAndIsDefaultIsFalse(currentUser.getId(), id)) {
			addressRepository.removeDefaultAddress(currentUser.getId());
			addressRepository.setDefaultAddress(currentUser.getId(), id);
		} else {
			throw new ApiBadRequestException("User does not contain provided address id or its already default!");
		}
	}

	private Address mapCommandToAddress(AddressCommand command) {
		return new Address(
				command.getCity(),
				command.getPostalCode(),
				command.getAddressLine(),
				command.getStreetNumber()
		);
	}

	private AddressDto mapAddressToDto(Address address) {
		return new AddressDto(
				address.getId(),
				address.getCity(),address.getPostalCode(),
				address.getAddressLine(),
				address.getStreetNumber()
		);
	}

	private User getCurrentUser(){
		return userRepository.findByEmail(securityUtils.getAuthenticatedUser().getName()).orElseThrow(
				() -> new ApiBadRequestException("Current user is not found in database")
		);
	}

	private Long addressExists(AddressCommand command){
		return addressRepository.findExistingAddress(
				command.getCity(),
				command.getPostalCode(),
				command.getAddressLine(),
				command.getStreetNumber()
		);
	}
}
