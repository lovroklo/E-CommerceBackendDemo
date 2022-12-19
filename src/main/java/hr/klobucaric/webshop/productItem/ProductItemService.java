package hr.klobucaric.webshop.productItem;

import java.util.Set;

public interface ProductItemService {

	ProductItemDto save(ProductItemCommand productItemCommand);

	ProductItemDto findByVariationOptions(Long productId, Set<Long> variationOptionIdSet);

}
