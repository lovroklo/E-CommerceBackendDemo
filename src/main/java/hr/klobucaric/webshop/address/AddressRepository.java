package hr.klobucaric.webshop.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Address save(Address address);

	@Query("""
		SELECT a.id 
		FROM Address a
		WHERE (
			a.city=:city 
			AND a.postalCode = :postalCode 
			AND a.addressLine = :addressLine 
			AND a.streetNumber = :streetNumber
		)
      """)
	Long findExistingAddress(String city, Integer postalCode, String addressLine, Integer streetNumber);

	@Query("""
	SELECT COUNT (a)>0 
	FROM Address a
	WHERE  a.id = :id
	""")
	Boolean addressExists(Long id);


	@Query("""
	SELECT COUNT (a) > 0 FROM UserAddress a
	WHERE (
		a.address.id = :addressId 
		AND a.user.id = :userId
	) 
	""")
	Boolean userContainsAddress(Long userId, Long addressId);

	@Query("""
	SELECT COUNT (a) > 0 
	FROM UserAddress a
	WHERE (
		a.address.id = :addressId 
		AND a.user.id = :userId  
		AND a.isDefault = FALSE 

	)
	""")
	Boolean userContainsAddressAndIsDefaultIsFalse(Long userId, Long addressId);

	@Modifying
	@Query("""
        UPDATE UserAddress u 
        SET u.isDefault =  false 
        WHERE u.user.id = :userId
    """)
	void removeDefaultAddress(Long userId);

	@Modifying
	@Query("""
        UPDATE UserAddress u 
        SET  u.isDefault =  TRUE 
        WHERE (
            u.user.id = :userId 
            AND u.address.id = :addressId
        )
    """)
	void setDefaultAddress(Long userId, Long addressId);
}
