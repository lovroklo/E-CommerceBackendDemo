package hr.klobucaric.webshop.shopOrder;

import hr.klobucaric.webshop.orderLine.OrderLineDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public record ShopOrderDto(Long id, Date orderDate, BigDecimal orderTotal, Long userId, Long shippingAddressId,
                           Long shippingMethodId, String orderStatus, Long paymentMethodId, Set<OrderLineDto> orderLineIds){
}
