package hr.klobucaric.webshop.category;

import hr.klobucaric.webshop.product.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
      SELECT new hr.klobucaric.webshop.category.CategoryDto(c.id, c.name) FROM Category c
      WHERE c.id=:id
      """)
    CategoryDto findCategoryDtoById(Long id);

    @Query("""
      SELECT new hr.klobucaric.webshop.category.CategoryDto(c.id, c.name) FROM Category c
      WHERE c.parentCategory is null 
      """)
    List<CategoryDto> findCategoryDtosByParentCategoryIsNull();

}
