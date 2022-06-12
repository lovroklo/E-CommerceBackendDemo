package hr.tvz.klobucaric.hardwareapp.repository;
import hr.tvz.klobucaric.hardwareapp.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByEmail(String email);



}
