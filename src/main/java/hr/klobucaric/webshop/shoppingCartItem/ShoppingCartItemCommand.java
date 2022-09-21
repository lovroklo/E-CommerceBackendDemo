package hr.klobucaric.webshop.shoppingCartItem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@AllArgsConstructor
@Getter
public class ShoppingCartItemCommand {

    @NotNull(message = "Quantity can't be null!")
    @Positive(message = "Quantity has to be a positive number!")
    private Integer qty;

    @NotNull(message = "Product item id can't be null!")
    private Long productItemId;

}
