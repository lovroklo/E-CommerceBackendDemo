package hr.klobucaric.webshop.shoppingCart;


import hr.klobucaric.webshop.security.SecurityUtils;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService{

    private final ShoppingCartRepository shoppingCartRepository;
    private final SecurityUtils securityUtils;

    @Override
    public BigDecimal getTotalPrice(){
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_Email(securityUtils.getAuthenticatedUser().getName()).orElseThrow(
                () -> new  ApiBadRequestException("There was something wrong while fetching shopping cart")
        );
        BigDecimal totalPrice = new BigDecimal(0);
        shoppingCart.getShoppingCartItems().forEach(shoppingCartItem ->
                totalPrice.add(shoppingCartItem.getProductItem().getPrice().multiply(new BigDecimal(shoppingCartItem.getQty())))
        );
        return totalPrice;
    }

}
