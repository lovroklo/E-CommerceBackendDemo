package hr.tvz.klobucaric.hardwareapp.services;


import hr.tvz.klobucaric.hardwareapp.command.AddToCartCommand;
import hr.tvz.klobucaric.hardwareapp.dto.cart.CartItemDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public interface CartService {

    Optional<CartItemDto> addToCart(AddToCartCommand cartCommand, String username) throws Exception;
    void deleteCartItem(String hardwareCode, String username) throws Exception;
    BigDecimal getTotaPrice(String userName);

}
