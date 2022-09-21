package hr.klobucaric.webshop.productItem;

import hr.klobucaric.webshop.orderLine.OrderLine;
import hr.klobucaric.webshop.product.Product;
import hr.klobucaric.webshop.shoppingCartItem.ShoppingCartItem;
import hr.klobucaric.webshop.variationOption.VariationOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_item")
@Getter
@Setter
@NoArgsConstructor
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String SKU;
    private Integer qtyInStock;
    private String productImage;
    private BigDecimal price;
    private String prodConfStr;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_configuration", joinColumns = @JoinColumn(name = "product_item_id"),
    inverseJoinColumns = @JoinColumn(name = "variation_option_id"))
    Set<VariationOption> variationOptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productItem")
    private Set<OrderLine> orderLines = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productItem")
    private Set<ShoppingCartItem> shoppingCartItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductItem that)) return false;
        return Objects.equals(SKU, that.SKU);
    }

    @Override
    public int hashCode() {
        return SKU != null ? SKU.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id=" + id +
                ", SKU='" + SKU + '\'' +
                ", qtyInStock=" + qtyInStock +
                ", productImage='" + productImage + '\'' +
                ", price=" + price +
                '}';
    }
}