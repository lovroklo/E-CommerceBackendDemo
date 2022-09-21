package hr.klobucaric.webshop.productItem;

import hr.klobucaric.webshop.variationOption.VariationOptionDto;
import java.math.BigDecimal;
import java.util.Set;

public record ProductItemDto(Long id, String SKU, Integer qtyInStock, String productImage, BigDecimal price, String name, String description
        , Set<VariationOptionDto> variationOptions
) {
}
