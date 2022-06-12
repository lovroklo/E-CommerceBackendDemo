package hr.tvz.klobucaric.hardwareapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "USER_ORDER")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id","cartItemList"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String city;
    private String address;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "post_code")
    private String postCode;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<CartItem> cartItem;

    public Order(BigDecimal totalPrice, String firstName, String lastName,
                 String city, String address, String email, String phoneNumber,
                 String postCode, Set<CartItem> cartItemList) {
        this.totalPrice = totalPrice;
        this.createdDate = LocalDateTime.now();
        this.firstName = capitalizeWords(firstName);
        this.lastName = capitalizeWords(lastName);
        this.city = capitalizeWords(city);
        this.address = capitalizeWords(address);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
        this.cartItem = cartItemList;
    }

    private String capitalizeWords(String string){
        string = string.toLowerCase();
        return Character.toString(string.charAt(0)).toUpperCase()+string.substring(1);
    }
}
