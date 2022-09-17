package hr.klobucaric.webshop.paymentMethod;

import hr.klobucaric.webshop.paymentType.PaymentType;
import hr.klobucaric.webshop.shopOrder.ShopOrder;
import hr.klobucaric.webshop.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user_payment_method")
@Getter
@Setter
@NoArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String provider;
    private String accountNumber;
    private Date expiryDate;
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL)
    private Set<ShopOrder> shopOrders;
}