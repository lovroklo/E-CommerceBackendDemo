package hr.klobucaric.webshop.category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

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

    @EntityGraph(attributePaths = {"parentCategory"})
    Optional<Category> findById(Long id);

}
