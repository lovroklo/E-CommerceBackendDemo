package hr.klobucaric.webshop.orderStatus;

import hr.klobucaric.webshop.shopOrder.ShopOrder;
import hr.klobucaric.webshop.utils.enums.OrderStatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_status")
@Getter
@Setter
@NoArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatusType status;

    @OneToMany(mappedBy = "orderStatus", cascade = CascadeType.ALL)
    private Set<ShopOrder> shopOrders = new HashSet<>();;

}