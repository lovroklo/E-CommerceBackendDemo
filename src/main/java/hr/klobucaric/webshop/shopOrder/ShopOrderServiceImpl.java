package hr.klobucaric.webshop.shopOrder;

import hr.klobucaric.webshop.address.AddressRepository;
import hr.klobucaric.webshop.orderLine.OrderLine;
import hr.klobucaric.webshop.orderLine.OrderLineDto;
import hr.klobucaric.webshop.orderLine.OrderLineRepository;
import hr.klobucaric.webshop.paymentMethod.PaymentMethodRepository;
import hr.klobucaric.webshop.productItem.ProductItemRepository;
import hr.klobucaric.webshop.security.SecurityUtils;
import hr.klobucaric.webshop.shippingMethod.ShippingMethodRepository;
import hr.klobucaric.webshop.shoppingCart.ShoppingCart;
import hr.klobucaric.webshop.shoppingCart.ShoppingCartRepository;

import hr.klobucaric.webshop.shoppingCartItem.ShoppingCartItem;
import hr.klobucaric.webshop.shoppingCartItem.ShoppingCartItemRepository;
import hr.klobucaric.webshop.user.UserRepository;
import hr.klobucaric.webshop.utils.enums.OrderStatus;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopOrderServiceImpl implements ShopOrderService {

	private final ShopOrderRepository shopOrderRepository;
	private final UserRepository userRepository;
	private final PaymentMethodRepository paymentMethodRepository;
	private final AddressRepository addressRepository;
	private final ShippingMethodRepository shippingMethodRepository;
	private final ShoppingCartRepository shoppingCartRepository;
	private final OrderLineRepository orderLineRepository;
	private final ShoppingCartItemRepository shoppingCartItemRepository;
	private final SecurityUtils securityUtils;

	@Transactional
	@Override
	public ShopOrderDto placeOrder(ShopOrderCommand shopOrderCommand) {
		String loggedInEmail = securityUtils.getAuthenticatedUser().getName();
		ShoppingCart shoppingCart = shoppingCartRepository.findByUser_Email(loggedInEmail).orElseThrow(
				() -> new ApiBadRequestException("Something went wrong while fetching shopping cart")
		);
		log.info("Fetched shopping cart for user that has email: "+loggedInEmail);

		Set<ShoppingCartItem> shoppingCartItems = shoppingCartRepository.findAllShopCartItemsDto(shoppingCart.getId());
		if(shoppingCartItems.isEmpty()) {
			log.error("Shopping cart that belongs to user with email: " + loggedInEmail + " is empty!");
			throw new ApiBadRequestException("Shopping cart that belongs to user with email: " + loggedInEmail + " is empty!");
		}
		log.info("Successfully fetched shoppingCartItems");

		ShopOrder order = shopOrderRepository.save(mapCommandToShopOrder(shopOrderCommand));
		Set<OrderLineDto> orderLineDtos = new HashSet<>();
		for(ShoppingCartItem cartItem : shoppingCartItems){
			OrderLine orderLine = new OrderLine();
			orderLine.setQty(cartItem.getQty());
			orderLine.setProductItem(cartItem.getProductItem());
			orderLine.setShopOrder(order);
			orderLine.setPrice(orderLine.getProductItem().getPrice());
			Integer qtyInStock = orderLine.getProductItem().getQtyInStock();
			if(qtyInStock<orderLine.getQty()) {
				throw new ApiBadRequestException("There is not enough product items in stock for this order!");
			}
			orderLine.getProductItem().setQtyInStock(qtyInStock-orderLine.getQty());
			orderLineDtos.add(mapOrderLineToDto(orderLineRepository.save(orderLine)));
		}
		shoppingCartItemRepository.deleteAll(shoppingCartItems);
		log.info("Transformed product items to order line objects and deleted all existing product items for current cart");

		return mapShopOrderToDto(order, orderLineDtos);
	}

	private ShopOrder mapCommandToShopOrder(ShopOrderCommand command) {
		ShopOrder order = new ShopOrder();
		order.setUser(
				userRepository.findByEmail(securityUtils.getAuthenticatedUser().getName()).orElseThrow(
						() -> new ApiBadRequestException("Error occurred while searching for authenticated user in database"))
		);
		order.setPaymentMethod(paymentMethodRepository.findById(command.getPaymentMethodId()).orElseThrow(
					() -> new ApiNotFoundException("Payment method is not found in database, id -> "+ command.getPaymentMethodId())
				)
		);
		order.setShippingAddress(addressRepository.findById(command.getShippingAddressId()).orElseThrow(
				() -> new ApiNotFoundException("Address is not found in database, id -> "+ command.getShippingAddressId())
		));
		order.setShippingMethod(shippingMethodRepository.findById(command.getShippingMethodId()).orElseThrow(
				() -> new ApiNotFoundException("Shipping method is not found in database, id -> "+ command.getShippingMethodId())
		));
		order.setOrderStatus(OrderStatus.PENDING);
		return order;
	}

	private ShopOrderDto mapShopOrderToDto(ShopOrder shopOrder, Set<OrderLineDto> orderLineDtos) {
		return new ShopOrderDto(
				shopOrder.getId(),
				shopOrder.getOrderDate(),
				shopOrder.getOrderTotal(),
				shopOrder.getUser().getId(),
				shopOrder.getShippingAddress().getId(),
				shopOrder.getShippingMethod().getId(),
				shopOrder.getOrderStatus().name(),
				shopOrder.getPaymentMethod().getId(),
				orderLineDtos
		);
	}

	private OrderLineDto mapOrderLineToDto(OrderLine orderLine){
		return new OrderLineDto(
				orderLine.getId(),
				orderLine.getQty(),
				orderLine.getPrice(),
				orderLine.getProductItem().getId(),
				orderLine.getShopOrder().getId()
		);
	}

}
