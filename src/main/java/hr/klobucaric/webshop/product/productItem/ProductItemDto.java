package hr.klobucaric.webshop.product.productItem;

import java.math.BigDecimal;

public record ProductItemDto(Long id, String SKU, Integer qtyInStock, String productImage, BigDecimal price, String name, String description) {
}
