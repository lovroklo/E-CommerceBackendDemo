package hr.klobucaric.webshop.productItem;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

	Boolean existsByProdConfStr(String str);

	Optional<ProductItem> findByProduct_IdAndProdConfStr(Long productId, String prodConfStr);

}
