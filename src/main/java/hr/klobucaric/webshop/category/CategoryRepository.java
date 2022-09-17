package hr.klobucaric.webshop.category;

import hr.klobucaric.webshop.product.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


   @Query("""
      select new hr.klobucaric.webshop.product.ProductDto(p.id, p.name, p.description, p.productImage) from Product p
      where p.category.id = :id or p.category.path like :path or p.category.path like :path2
    """)
   List<ProductDto> findAllProductsByCategoryId(Long id, String path, String path2);


    @Query("""
      select new hr.klobucaric.webshop.product.ProductDto(p.id, p.name, p.description, p.productImage) from Product p
      where p.category.name = :name or p.category.path like :path or p.category.path like :path2
    """)
    List<ProductDto> findAllProductsByCategoryId(String name, String path, String path2);

    /*SELECT new hr.klobucaric.webshop.product.ProductDto(p.id, p.name, p.description,p.productImage) FROM
            (select * from Category)*/
}
