package hr.klobucaric.webshop.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address save(Address address);

    Optional<Address> findByCityAndPostalCodeAndAddressLineAndStreetNumber(
            String city, Integer postalCode, String addressLine, Integer streetNumber
    );
}
