package hr.klobucaric.webshop.productItem;


import hr.klobucaric.webshop.variationOption.VariationOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {



    Boolean existsByProdConfStr(String str);

    Optional<ProductItem> findByProduct_IdAndProdConfStr(Long productId, String prodConfStr);

}
