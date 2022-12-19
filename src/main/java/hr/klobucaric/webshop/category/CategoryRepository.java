package hr.klobucaric.webshop.category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("""
	SELECT new hr.klobucaric.webshop.category.CategoryDto(c.id, c.name, c.path) 
	FROM Category c
	WHERE c.id=:id
      """)
	Optional<CategoryDto> findCategoryDtoById(@Param("id") Long id);

	@Query("""
	SELECT new hr.klobucaric.webshop.category.CategoryDto(c.id, c.name, c.path) 
	FROM Category c
	WHERE c.parentCategory IS NULL 
      """)
	List<CategoryDto> findCategoryDtosByParentCategoryIsNull();

	@Query("""
	SELECT CASE 
	WHEN COUNT (c)>0 THEN TRUE ELSE FALSE END 
	FROM Category c 
	WHERE c.parentCategory.id = :id
    """)
	Boolean categoryHasChildCategories(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE product p set p.category_id = :categoryId where p.category_id = :id")
	void setCategoryIdToParentCategoryIdOnProducts(@Param("id") Long id, @Param("categoryId") Long categoryId);

	@EntityGraph(attributePaths = {"parentCategory"})
	Optional<Category> findById(@Param("id") Long id);


}
