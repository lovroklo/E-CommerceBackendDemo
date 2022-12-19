package hr.klobucaric.webshop.shopOrder;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {
}