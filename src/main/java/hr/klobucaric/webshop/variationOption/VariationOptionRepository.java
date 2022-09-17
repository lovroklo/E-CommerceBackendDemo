package hr.klobucaric.webshop.variationOption;

import hr.klobucaric.webshop.variation.VariationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface VariationOptionRepository extends JpaRepository<VariationOption, Long> {

    @Query("""
      select new hr.klobucaric.webshop.variationOption.VariationOptionDto(v.id, v.variation.id, v.value) from VariationOption v
      where v.variation.id = :v
    """)
    Set<VariationOptionDto> findVariationDtoSetByVariationId(Long id);

    @Query("""
      select new hr.klobucaric.webshop.variationOption.VariationOptionDto(v.id,v.variation.id ,v.value) from VariationOption v
      where v.id = :id
    """)
    VariationOptionDto findDtoById(Long id);

}