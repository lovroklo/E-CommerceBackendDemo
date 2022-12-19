package hr.klobucaric.webshop.shoppingCartItem;

import hr.klobucaric.webshop.productItem.ProductItemRepository;
import hr.klobucaric.webshop.security.SecurityUtils;
import hr.klobucaric.webshop.shoppingCart.ShoppingCartRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

	private final ShoppingCartItemRepository shoppingCartItemRepository;
	private final ShoppingCartRepository shoppingCartRepository;
	private final SecurityUtils securityUtils;
	private final ProductItemRepository productItemRepository;

	@Transactional
	@Override
	public ShoppingCartItemDto addToCart(ShoppingCartItemCommand command) {
		return mapShoppingCartToDto(
				shoppingCartItemRepository.save(mapCommandToShoppingCartItem(command))
		);

	}

	private ShoppingCartItemDto mapShoppingCartToDto(ShoppingCartItem cartItem) {
		return new ShoppingCartItemDto(
				cartItem.getId(),
				cartItem.getQty(),
				cartItem.getProductItem().getId(),
				cartItem.getShoppingCart().getId()
		);
	}

	private ShoppingCartItem mapCommandToShoppingCartItem(ShoppingCartItemCommand command) {
		ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
		shoppingCartItem.setShoppingCart(shoppingCartRepository.findByUser_Email(
						securityUtils.getAuthenticatedUser().getName()).orElseThrow(
						() -> new ApiBadRequestException("There was something wrong while fetching shopping cart")
				)
		);

		shoppingCartItem.setProductItem(productItemRepository.findById(
						command.getProductItemId()).orElseThrow(
						() -> new ApiNotFoundException("There is no product item with provided id="+command.getProductItemId())
				)
		);

		if(shoppingCartItem.getProductItem().getQtyInStock() < command.getQty()) {
			throw new ApiBadRequestException("There is not enough product items in stock for this order!");
		}

		shoppingCartItem.setQty(command.getQty());
		return shoppingCartItem;
	}

}
