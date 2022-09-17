package hr.klobucaric.webshop.address;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class UserAddressKey implements Serializable {

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "address_id")
    Integer addressId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAddressKey)) return false;

        UserAddressKey that = (UserAddressKey) o;

        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        return result;
    }


}