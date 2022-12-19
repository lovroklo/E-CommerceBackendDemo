package hr.klobucaric.webshop.variationOption;

import java.util.Set;

public interface VariationOptionService {

	Set<VariationOptionDto> findVariationOptionsByVariationId(Long categoryId);

	VariationOptionDto findVariationOptionById(Long id);

	VariationOptionDto save(final VariationOptionCommand command);
}
