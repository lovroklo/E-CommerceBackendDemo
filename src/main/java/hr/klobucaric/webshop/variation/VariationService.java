package hr.klobucaric.webshop.variation;


import java.util.Set;

public interface VariationService {

	Set<VariationDto> findVariationsByCategoryId(Long categoryId);

	VariationDto findVariationById(Long id);

	VariationDto save(final VariationCommand variationCommand);

}
