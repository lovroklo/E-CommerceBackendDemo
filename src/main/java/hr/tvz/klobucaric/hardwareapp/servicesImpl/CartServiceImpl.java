package hr.tvz.klobucaric.hardwareapp.servicesImpl;

import hr.tvz.klobucaric.hardwareapp.command.AddToCartCommand;
import hr.tvz.klobucaric.hardwareapp.domain.CartItem;
import hr.tvz.klobucaric.hardwareapp.domain.Hardware;
import hr.tvz.klobucaric.hardwareapp.domain.User;
import hr.tvz.klobucaric.hardwareapp.dto.cart.CartItemDto;
import hr.tvz.klobucaric.hardwareapp.repository.CartItemRepository;
import hr.tvz.klobucaric.hardwareapp.repository.HardwareRepository;
import hr.tvz.klobucaric.hardwareapp.repository.UserRepository;
import hr.tvz.klobucaric.hardwareapp.services.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {


    private HardwareRepository hardwareRepository;
    private UserRepository userRepository;
    private CartItemRepository cartItemRepository;
    public CartServiceImpl(HardwareRepository hardwareRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.hardwareRepository = hardwareRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Optional<CartItemDto> addToCart(AddToCartCommand cartCommand, String username) {

        Optional<User> user = userRepository.findByUsername(username);
        Optional<Hardware> hardware = hardwareRepository.findById(cartCommand.getHardwareId());

        if (user.isPresent()&&hardware.isPresent()){
            if (cartCommand.getQuantity()>hardware.get().getStock()){
                cartCommand.setQuantity(hardware.get().getStock());
            }
            Optional<CartItem> cartItem = cartItemRepository.findByUserAndHardware(user.get(),hardware.get());
            if(cartItem.isPresent())
            {
                cartItem.get().setQuantity(cartCommand.getQuantity());
            }else{
                cartItem = Optional.of(new CartItem(cartCommand.getQuantity(),hardware.get(), user.get()));
            }

            cartItemRepository.save(cartItem.get());

            return Optional.of(mapCartItemToDto(cartItem.get()));
        }else {
            return Optional.empty();
        }

    }

    @Override
    public void deleteCartItem(String hardwareCode, String username) throws Exception {

        Optional<User> user = userRepository.findByUsername(username);
        Optional<Hardware> hardware = hardwareRepository.findByCode(hardwareCode);
        if(hardware.isPresent()&& user.isPresent()) {
            Optional<CartItem> cartItem = cartItemRepository.findByUserAndHardware(user.get(),hardware.get());
            if(cartItem.isPresent()){
                cartItemRepository.delete(cartItem.get());
            }
        }
    }

    @Override
    public BigDecimal getTotaPrice(String userName) {
        return cartItemRepository.getTotalPrice(userName);
    }

    public CartItemDto mapCartItemToDto(CartItem cartItem){
        return new CartItemDto(cartItem.getHardware().getCode(), cartItem.getQuantity());
    }


}
