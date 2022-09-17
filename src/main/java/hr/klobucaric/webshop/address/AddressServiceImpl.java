package hr.klobucaric.webshop.address;

import hr.klobucaric.webshop.security.SecurityUtils;
import hr.klobucaric.webshop.user.User;
import hr.klobucaric.webshop.user.UserRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


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
        Optional<Address> existingAddress = addressExists(addressCommand);
        Boolean isDefault = false;
        Boolean userContainsExistingAddress = existingAddress.isPresent() ?
                currentUser.getUserAddresses().stream()
                        .filter(userAddress -> userAddress.getAddress().getId().equals(existingAddress.get().getId()))
                        .findFirst().isPresent() : false;
        if(userContainsExistingAddress){
            throw new ApiBadRequestException("Logged in user already contains this address!");
        } else if(!currentUser.getUserAddresses().isEmpty() || addressCommand.getIsDefault()){
            isDefault=true;
            currentUser.getUserAddresses().stream()
                    .filter(userAddress -> userAddress.getIsDefault().equals(true))
                    .findFirst().ifPresent(userAddress -> userAddress.setIsDefault(false));
        }
        Address address = mapCommandToAddress(addressCommand);
        address.addUserAddress(currentUser, isDefault);
        return mapAddressToDto(addressRepository.save(address));
    }

    @Override
    public void setDefaultAddress(Integer id) {
        User currentUser = getCurrentUser();
        Optional<Address> existingAddress = addressRepository.findById(id);
            AtomicReference<Boolean> isPresent = new AtomicReference<>(false);
        if(existingAddress.isPresent()){
            currentUser.getUserAddresses().stream()
                    .filter(userAddress -> userAddress.getAddress().getId().equals(existingAddress.get().getId()))
                    .findFirst().ifPresent(userAddress ->  {
                        userAddress.setIsDefault(true);
                        isPresent.set(true);
                    });
        }else {
            throw new ApiNotFoundException("There is no address with provided id");
        }
        if(isPresent.get().equals(false))
            throw new ApiBadRequestException("User does not contain provided address id!");
    }

    private Address mapCommandToAddress(AddressCommand command){
        return new Address(command.getCity(),command.getPostalCode(),command.getAddressLine(),command.getStreetNumber());
    }

    private AddressDto mapAddressToDto(Address address){
        return new AddressDto(address.getId(),address.getCity(),address.getPostalCode(),address.getAddressLine(),address.getStreetNumber());
    }

    private User getCurrentUser(){
       return userRepository.findByEmail(securityUtils.getAuthenticatedUser().getName()).get();
    }
    private Optional<Address> addressExists(AddressCommand command){
        return addressRepository.findByCityAndPostalCodeAndAddressLineAndStreetNumber(
                command.getCity(),command.getPostalCode(),command.getAddressLine(),command.getStreetNumber()
        );
    }
}
