package hr.klobucaric.webshop.variationOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface VariationOptionRepository extends JpaRepository<VariationOption, Long> {

    @Query("""
      select new hr.klobucaric.webshop.variationOption.VariationOptionDto(v.id, v.id, v.value) from VariationOption v
      where v.variation.id = :id
    """)
    Set<VariationOptionDto> findVariationDtoSetByVariationId(Long id);

    @Query("""
      select new hr.klobucaric.webshop.variationOption.VariationOptionDto(v.id, v.variation.id , v.value) from VariationOption v
      where v.id = :id
    """)
    VariationOptionDto findDtoById(Long id);

    Set<VariationOption> findAllByIdIn(Set<Long> id);
}