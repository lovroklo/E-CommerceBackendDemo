package hr.tvz.klobucaric.hardwareapp.controller;

import hr.tvz.klobucaric.hardwareapp.command.AddToCartCommand;
import hr.tvz.klobucaric.hardwareapp.domain.CartItem;
import hr.tvz.klobucaric.hardwareapp.domain.User;
import hr.tvz.klobucaric.hardwareapp.dto.cart.CartItemDto;
import hr.tvz.klobucaric.hardwareapp.dto.cart.CartItemsDto;
import hr.tvz.klobucaric.hardwareapp.exceptions.UserNotLoggedIn;
import hr.tvz.klobucaric.hardwareapp.security.ApplicationUser;
import hr.tvz.klobucaric.hardwareapp.services.CartService;
import hr.tvz.klobucaric.hardwareapp.services.HardwareService;
import hr.tvz.klobucaric.hardwareapp.servicesImpl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    private CartService cartService;
    private UserService userService;
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addToCart(@Valid @RequestBody AddToCartCommand cartCommand, @AuthenticationPrincipal ApplicationUser applicationUser) throws Exception {

        return cartService.addToCart(cartCommand, applicationUser.getUsername())
                .map(
                        cartItemDto -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(cartItemDto)
                ).orElseGet(
                        ()->ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .build()
                );
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/cartItems")
    public ResponseEntity<?> listCartItems(@AuthenticationPrincipal ApplicationUser applicationUser) throws Exception {
        Optional<User> user = userService.findUserByUsername(applicationUser.getUsername());

        if(user.isPresent()){

             CartItemsDto cartItemsDto = new CartItemsDto();

             cartItemsDto.setListOfItems(user.get().getCartItems().stream()
                     .map(this::mapCartItemToCartItemDto)
                     .collect(Collectors.toSet()));

             cartItemsDto.setTotalCost(cartService.getTotaPrice(user.get().getUsername()));

             return new ResponseEntity<>(cartItemsDto,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new UserNotLoggedIn("user is not logged in"),HttpStatus.UNAUTHORIZED);
        }




    }
  @Secured({"ROLE_USER"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code, @AuthenticationPrincipal ApplicationUser applicationUser) throws Exception {

        cartService.deleteCartItem(code,applicationUser.getUsername());

    }


    private CartItemDto mapCartItemToCartItemDto(CartItem cartItem){
       return new CartItemDto(cartItem.getHardware().getCode(),cartItem.getQuantity());
    }


}
