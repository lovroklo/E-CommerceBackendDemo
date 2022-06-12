package hr.tvz.klobucaric.hardwareapp.domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Table(name = "CART_ITEM")
@Getter
@Setter
@EqualsAndHashCode(of = {"hardware","user"})
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hardware_id")
    private Hardware hardware;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public CartItem(Integer quantity, Hardware hardware, User user) {
        this.quantity = quantity;
        this.hardware = hardware;
        this.user = user;
    }

}
