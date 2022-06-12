package hr.tvz.klobucaric.hardwareapp.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CartItemsDto {

        Set<CartItemDto> listOfItems;
        BigDecimal totalCost;


}
