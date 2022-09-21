package hr.klobucaric.webshop.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Address save(Address address);

    @Query("""
      select a.id from Address a
      where  a.city=:city and  a.postalCode = :postalCode and a.addressLine = :addressLine and a.streetNumber = :streetNumber
      """)
    Long findExistingAddress(String city, Integer postalCode, String addressLine, Integer streetNumber);

    @Query("""
      select count (a)>0 from Address a
      where  a.id = :id
      """)
    Boolean addressExists(Long id);


    @Query("""
      select count(a) > 0 FROM UserAddress a
      where  a.address.id = :addressId and a.user.id = :userId
      """)
    Boolean userContainsAddress(Long userId, Long addressId);

    @Query("""
      select count(a) > 0 FROM UserAddress a
      where  a.address.id = :addressId and a.user.id = :userId  and a.isDefault=false 
      """)
    Boolean userContainsAddressAndIsDefaultIsFalse(Long userId, Long addressId);

    @Modifying
    @Query("""
        update UserAddress u set  u.isDefault =  false 
        where u.user.id = :userId
    """)
    void removeDefaultAddress(Long userId);

    @Modifying
    @Query("""
        update UserAddress u set  u.isDefault =  true 
        where u.user.id = :userId and u.address.id = :addressId
    """)
    void setDefaultAddress(Long userId, Long addressId);
}
