package hr.klobucaric.webshop.orderLine;

import hr.klobucaric.webshop.product.productItem.ProductItem;
import hr.klobucaric.webshop.shopOrder.ShopOrder;
import hr.klobucaric.webshop.userReview.UserReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "order_line")
@Getter
@Setter
@NoArgsConstructor
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer qty;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "product_item_id")
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private ShopOrder shopOrder;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "orderedProduct")
    private Set<UserReview> userReviews = new HashSet<>();

}