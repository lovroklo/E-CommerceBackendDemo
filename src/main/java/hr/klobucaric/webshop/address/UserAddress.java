package hr.klobucaric.webshop.address;

import hr.klobucaric.webshop.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_address")
@NoArgsConstructor
@Getter
@Setter
public class UserAddress implements Serializable {
 //todo add later when using product - @CreationTimestamp @Temporal(TemporalType.TIMESTAMP)
    @EmbeddedId
    private UserAddressKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(name = "address_id")
    private Address address;

    private Boolean isDefault;

    public UserAddress(User user, Address address, Boolean isDefault) {
        this.id = new UserAddressKey(user.getId(), address.getId());
        this.user = user;
        this.address = address;
        this.isDefault = isDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAddress)) return false;

        UserAddress that = (UserAddress) o;

        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
