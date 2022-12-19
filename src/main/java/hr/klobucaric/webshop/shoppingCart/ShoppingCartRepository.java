package hr.klobucaric.webshop.shoppingCart;

import hr.klobucaric.webshop.shoppingCartItem.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {


	Optional<ShoppingCart> findByUser_Email(String name);

	@Query("""
      SELECT s 
      FROM ShoppingCartItem s
      WHERE s.shoppingCart.id = :id
      """)
	Set<ShoppingCartItem> findAllShopCartItemsDto(Long id);


}