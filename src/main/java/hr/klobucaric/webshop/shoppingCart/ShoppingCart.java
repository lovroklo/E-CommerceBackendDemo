package hr.klobucaric.webshop.shoppingCart;

import hr.klobucaric.webshop.shoppingCartItem.ShoppingCartItem;
import hr.klobucaric.webshop.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart")
    private Set<ShoppingCartItem> shoppingCartItems;

}