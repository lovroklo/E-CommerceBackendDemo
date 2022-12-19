package hr.klobucaric.webshop.orderLine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

	@Query("""
        SELECT SUM(o.price) 
        FROM OrderLine o 
        WHERE o.shopOrder.id = :orderId
    """)
	BigDecimal getShopOrderTotalPrice(Long orderId);
}