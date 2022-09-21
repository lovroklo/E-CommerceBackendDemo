package hr.klobucaric.webshop.productItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
public class ProductItemCommand {

    @NotBlank(message = "SKU can't be empty")
    private String SKU;
    @NotNull(message = "Quantity can't be null")
    private Integer qtyInStock;
    @URL(message = "Image type has to be url!")
    private String productImage;
    @NotNull(message = "Price can't be null")
    private BigDecimal price;
    @NotNull(message = "Id cant be null")
    private Long productId;
    @NotNull(message = "There should be at least on variation option")
    private Set<Long> variationOptionsId;

}
