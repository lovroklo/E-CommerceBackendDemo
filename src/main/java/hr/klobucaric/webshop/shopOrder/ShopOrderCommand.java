package hr.klobucaric.webshop.shopOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShopOrderCommand {

    private Long paymentMethodId;
    private Long shippingAddressId;
    private Long shippingMethodId;

}
