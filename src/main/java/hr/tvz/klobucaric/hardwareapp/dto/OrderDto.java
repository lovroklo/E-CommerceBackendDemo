package hr.tvz.klobucaric.hardwareapp.dto;
import hr.tvz.klobucaric.hardwareapp.dto.cart.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private String postCode;
    private BigDecimal totalPrice;
    private Set<CartItemDto> cartItems;
    private LocalDateTime created_date;


}
