package hr.klobucaric.webshop.variation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface VariationRepository extends JpaRepository<Variation, Long> {

    @Query("""
      select new hr.klobucaric.webshop.variation.VariationDto(v.id, v.name) from Variation v
      where v.category.id = :categoryId
    """)
    Set<VariationDto> findVariationDtoSetByCategoryId(Long categoryId);

    @Query("""
      select new hr.klobucaric.webshop.variation.VariationDto(v.id, v.name) from Variation v
      where v.id = :id
    """)
    VariationDto findDtoById(Long id);
}
