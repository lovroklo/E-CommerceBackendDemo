package hr.tvz.klobucaric.hardwareapp.services;

import hr.tvz.klobucaric.hardwareapp.command.OrderCommand;
import hr.tvz.klobucaric.hardwareapp.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> getAllOrders();

    List<OrderDto> getOrdersByEmail(String username);

    void deleteOrdersById(Long id);

    Optional<OrderDto> createOrder(OrderCommand orderCommand, String userName);
}
