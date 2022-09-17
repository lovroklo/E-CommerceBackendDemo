package hr.klobucaric.webshop.product.productItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ProductItemCommand {

    @NotBlank(message = "SKU can't be empty")
    private String SKU;
    @NotNull(message = "Quantity can't be null")
    private Integer qtyInStock;
    @URL
    private String productImage;
    @NotNull(message = "Price can't be null")
    private BigDecimal price;
    @NotNull(message = "Id cant be null")
    private Long productId;
}
