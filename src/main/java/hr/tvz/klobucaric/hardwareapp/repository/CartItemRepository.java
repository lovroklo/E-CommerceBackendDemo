package hr.tvz.klobucaric.hardwareapp.repository;

import hr.tvz.klobucaric.hardwareapp.domain.CartItem;
import hr.tvz.klobucaric.hardwareapp.domain.Hardware;
import hr.tvz.klobucaric.hardwareapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserAndHardware(User user, Hardware hardware);

    @Query("select SUM(h.price*c.quantity) from CartItem c " +
            "inner join Hardware h on h.id = c.hardware.id " +
            "where c.user.username= ?1")
    BigDecimal getTotalPrice(String username);

    Set<CartItem> findAllByUser_Username(String username);
}
