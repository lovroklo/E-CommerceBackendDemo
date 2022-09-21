package hr.klobucaric.webshop.user;

import hr.klobucaric.webshop.address.UserAddress;
import hr.klobucaric.webshop.paymentMethod.PaymentMethod;
import hr.klobucaric.webshop.role.Role;
import hr.klobucaric.webshop.shopOrder.ShopOrder;
import hr.klobucaric.webshop.shoppingCart.ShoppingCart;
import hr.klobucaric.webshop.userReview.UserReview;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "site_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,
    name = "email_address")
    private String email;

    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<UserReview> userReviews = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<UserAddress> userAddresses = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ShopOrder> shopOrders = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PaymentMethod> paymentMethod = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ShoppingCart> shoppingCarts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "id")}
    )

    private Set<Role> roles = new HashSet<>();

    public User(String email, String phoneNumber, String password) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
