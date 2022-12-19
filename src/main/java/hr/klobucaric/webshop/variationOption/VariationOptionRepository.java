package hr.klobucaric.webshop.variationOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface VariationOptionRepository extends JpaRepository<VariationOption, Long> {

	@Query("""
      SELECT NEW hr.klobucaric.webshop.variationOption.VariationOptionDto(v.id, v.id, v.value) 
      FROM VariationOption v
      WHERE v.variation.id = :id
    """)
	Set<VariationOptionDto> findVariationDtoSetByVariationId(Long id);

	@Query("""
      SELECT NEW hr.klobucaric.webshop.variationOption.VariationOptionDto(v.id, v.variation.id , v.value) 
      FROM VariationOption v
      WHERE v.id = :id
    """)
	VariationOptionDto findDtoById(Long id);

	Set<VariationOption> findAllByIdIn(Set<Long> id);
}