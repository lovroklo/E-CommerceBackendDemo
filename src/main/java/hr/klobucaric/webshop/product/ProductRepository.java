package hr.klobucaric.webshop.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {



    @Query("""
      SELECT new hr.klobucaric.webshop.product.ProductDto(p.id, p.name, p.description, p.productImage) FROM Product p
      """)
    Page<ProductDto> findAllDto(Pageable name);

    @Query("""
      SELECT new hr.klobucaric.webshop.product.ProductDto(p.id, p.name, p.description, p.productImage) FROM Product p
      WHERE  p.id = :id
      """)
    Optional<ProductDto> findByIdDto(Long id);

    List<Product> findByCategoryName(String categoryName);
    Long deleteProductById(Long id);

}
