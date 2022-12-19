package hr.klobucaric.webshop.shoppingCartItem;



public record ShoppingCartItemDto(Long id, Integer qty, Long productItemId, Long shoppingCartId) {
}
