package hr.tvz.klobucaric.hardwareapp.servicesImpl;


import hr.tvz.klobucaric.hardwareapp.command.OrderCommand;
import hr.tvz.klobucaric.hardwareapp.domain.CartItem;
import hr.tvz.klobucaric.hardwareapp.domain.Order;
import hr.tvz.klobucaric.hardwareapp.dto.OrderDto;
import hr.tvz.klobucaric.hardwareapp.dto.cart.CartItemDto;
import hr.tvz.klobucaric.hardwareapp.repository.CartItemRepository;
import hr.tvz.klobucaric.hardwareapp.repository.OrderRepository;
import hr.tvz.klobucaric.hardwareapp.services.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private CartItemRepository cartItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapOrderToDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getOrdersByEmail(String email) {
        return orderRepository.findAllByEmail(email).stream().map(this::mapOrderToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteOrdersById(Long id) {
         orderRepository.deleteById(id);
    }

    @Override
    public Optional<OrderDto> createOrder(OrderCommand orderCommand, String userName)
    {
        Set<CartItem> currentUserCartItems = cartItemRepository.findAllByUser_Username(userName);
        BigDecimal totalPrice = cartItemRepository.getTotalPrice(userName);
        return Optional.of(mapOrderToDto(orderRepository.save(mapCommandToOrder(orderCommand,totalPrice,currentUserCartItems))));
    }

    private Order mapCommandToOrder(OrderCommand orderCommand, BigDecimal totalPrice, Set<CartItem> cartitemSet){
        return new Order(totalPrice, orderCommand.getFirstName(),orderCommand.getLastName(), orderCommand.getCity(),
        orderCommand.getAddress(), orderCommand.getEmail(), orderCommand.getPhoneNumber(), orderCommand.getPostCode(), cartitemSet);
    }
    private OrderDto mapOrderToDto(Order order){
        return new OrderDto(order.getId(), order.getFirstName(),order.getLastName(),order.getCity(),order.getAddress(),order.getEmail()
                ,order.getPhoneNumber() ,order.getPostCode(),order.getTotalPrice(), order.getCartItem().stream()
                .map(this::mapCartItemToCartItemDto).collect(Collectors.toSet()), order.getCreatedDate());
    }
    private CartItemDto mapCartItemToCartItemDto(CartItem cartItem){
        return new CartItemDto(cartItem.getHardware().getCode(), cartItem.getQuantity());
    }
}
