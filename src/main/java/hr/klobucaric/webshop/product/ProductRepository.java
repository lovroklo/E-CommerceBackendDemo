package hr.klobucaric.webshop.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

	@Query("""
      SELECT NEW hr.klobucaric.webshop.product.ProductDto(p.id, p.name, p.description, p.productImage, p.category.path) 
      FROM Product p
      """)
	Page<ProductDto> findProductDtoPage(Pageable name);

	@Query("""
      SELECT NEW hr.klobucaric.webshop.product.ProductDto(p.id, p.name, p.description, p.productImage, p.category.path) 
      FROM Product p
      WHERE  p.id = :id
      """)
	Optional<ProductDto> findOneProductDtoById(Long id);

	@Query("""
      SELECT NEW hr.klobucaric.webshop.product.ProductDto(p.id, p.name, p.description, p.productImage, p.category.path) 
      FROM Product p
      WHERE(
        p.category.id = :id 
        OR p.category.path LIKE CONCAT('%.',:id,'.%') 
        OR p.category.path LIKE CONCAT(:id,'.%')
      )
    """)
	Page<ProductDto> findProductDtoPageByCategoryId(Pageable pageable, Long id);

	Long deleteProductById(Long id);
}

