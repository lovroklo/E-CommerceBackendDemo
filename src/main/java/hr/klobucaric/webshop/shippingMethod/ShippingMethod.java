package hr.klobucaric.webshop.shippingMethod;

import hr.klobucaric.webshop.shopOrder.ShopOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shipping_method")
@Getter
@Setter
@NoArgsConstructor
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private BigDecimal price;

    @OneToMany(mappedBy = "shippingMethod", cascade = CascadeType.ALL)
    private Set<ShopOrder> shopOrders = new HashSet<>();;
}