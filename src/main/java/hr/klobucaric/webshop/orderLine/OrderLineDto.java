package hr.klobucaric.webshop.orderLine;

import java.math.BigDecimal;

public record OrderLineDto(Long id, Integer qty, BigDecimal price, Long productItemId,
                           Long shopOrderId) {
}

