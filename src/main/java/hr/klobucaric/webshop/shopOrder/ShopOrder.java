package hr.klobucaric.webshop.shopOrder;

import hr.klobucaric.webshop.address.Address;
import hr.klobucaric.webshop.orderLine.OrderLine;
import hr.klobucaric.webshop.orderStatus.OrderStatus;
import hr.klobucaric.webshop.paymentMethod.PaymentMethod;
import hr.klobucaric.webshop.paymentType.PaymentType;
import hr.klobucaric.webshop.shippingMethod.ShippingMethod;
import hr.klobucaric.webshop.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shop_order")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ShopOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_date", nullable = false, updatable = false)
    @CreatedDate
    private Date orderDate;
    private BigDecimal orderTotal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shipping_address")
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = "shipping_method")
    private ShippingMethod shippingMethod;

    @ManyToOne
    @JoinColumn(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "shopOrder")
    Set<OrderLine> orderLines = new HashSet<>();

}