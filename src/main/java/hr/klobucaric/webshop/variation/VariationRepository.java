package hr.klobucaric.webshop.variation;

import hr.klobucaric.webshop.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface VariationRepository extends JpaRepository<Variation, Long> {

	@Query("""
      SELECT NEW hr.klobucaric.webshop.variation.VariationDto(v.id, v.name) 
      FROM Variation v
      WHERE v.category.id = :categoryId
    """)
	Set<VariationDto> findVariationDtoSetByCategoryId(Long categoryId);

	@EntityGraph(attributePaths = {"category"})
	Optional<Variation> findById(Long id);

	@Query("""
      SELECT NEW hr.klobucaric.webshop.variation.VariationDto(v.id, v.name) 
      FROM Variation v
      WHERE v.id = :id
    """)
	VariationDto findDtoById(Long id);
}
