package hr.klobucaric.webshop.address;

import hr.klobucaric.webshop.shopOrder.ShopOrder;
import hr.klobucaric.webshop.user.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Address(String city, Integer postalCode, String addressLine, Integer streetNumber) {
        this.city = city;
        this.postalCode = postalCode;
        this.addressLine = addressLine;
        this.streetNumber = streetNumber;
    }

    private String city;
    private Integer postalCode;
    private String addressLine;
    private Integer streetNumber;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    Set<UserAddress> userAddresses = new HashSet<>();

    @OneToMany(mappedBy = "shippingAddress", cascade = CascadeType.ALL)
    private Set<ShopOrder> shopOrders = new HashSet<>();

    public void addUserAddress(User user, Boolean isDefault) {
        userAddresses.add( new UserAddress(user, this, isDefault));
    }

    //todo https://www.baeldung.com/jpa-many-to-many
    //todo boolean se mora promjeniti ostalima u nula dok se nekome dene na 1


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (!Objects.equals(id, address.id)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(postalCode, address.postalCode)) return false;
        if (!Objects.equals(addressLine, address.addressLine)) return false;
        return Objects.equals(streetNumber, address.streetNumber);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (addressLine != null ? addressLine.hashCode() : 0);
        result = 31 * result + (streetNumber != null ? streetNumber.hashCode() : 0);
        return result;
    }
}
