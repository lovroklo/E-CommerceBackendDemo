package hr.klobucaric.webshop.shoppingCartItem;

import javax.transaction.Transactional;

public interface ShoppingCartItemService {



    ShoppingCartItemDto addToCart(ShoppingCartItemCommand command);
}
