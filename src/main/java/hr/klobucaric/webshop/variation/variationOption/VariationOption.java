package hr.klobucaric.webshop.variation.variationOption;

import hr.klobucaric.webshop.product.productItem.ProductItem;
import hr.klobucaric.webshop.variation.Variation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "variation_option")
@Getter
@Setter
@NoArgsConstructor
public class VariationOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "variation_id")
    private Variation variation;

    @ManyToMany(mappedBy = "variationOptions")
    private Set<ProductItem> productItems = new HashSet<>();;

}