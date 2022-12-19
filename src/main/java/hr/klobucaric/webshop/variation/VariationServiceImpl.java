package hr.klobucaric.webshop.variation;

import hr.klobucaric.webshop.category.CategoryRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VariationServiceImpl implements VariationService {

	private final VariationRepository variationRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public Set<VariationDto> findVariationsByCategoryId(Long categoryId) {
		final Set<VariationDto> variationDtoSet;
		try {
			variationDtoSet = variationRepository.findVariationDtoSetByCategoryId(categoryId);
		} catch (IllegalArgumentException e){
			throw new ApiBadRequestException("Provided variation id is not in database! " +e.getMessage());
		} catch (Exception e){
			throw new ApiBadRequestException("Something went wrong in Service layer while fetching all variations by category id" + e.getMessage());
		}
		if(variationDtoSet.isEmpty()) {
			throw new ApiNotFoundException("There is no variation found in database by selected parameters");
		}
		return variationDtoSet;
	}

	@Override
	public VariationDto findVariationById(Long id) {
		try {
			return variationRepository.findDtoById(id);
		} catch (IllegalArgumentException e) {
			throw new ApiBadRequestException("Given variation id is null, please send some id to be searched." + e.getMessage());
		} catch (NoSuchElementException e){
			throw new ApiNotFoundException("Variation with id="+id+" does not exist in database. " + e.getMessage());
		} catch (Exception e){
			throw new ApiBadRequestException("Something went wrong in Service layer while fetching variation by id. " + e.getMessage());
		}
	}

	@Override
	public VariationDto save(VariationCommand variationCommand) {
		try {
			Variation variation = new Variation();
			variation.setCategory(
					categoryRepository.findById(variationCommand.getCategoryId()).orElseThrow(
							() -> new ApiNotFoundException("There is no category with id: "+ variationCommand.getCategoryId())
					));
			variation.setName(variationCommand.getName());
			return mapVariationToDto(variationRepository.save(variation));
		}catch (IllegalArgumentException e) {
			throw new ApiBadRequestException("Given variation is null." + e.getMessage());
		}catch (Exception e){
			throw new ApiBadRequestException("Something went wrong in service layer while saving the variation." + e.getMessage());
		}
	}


	private VariationDto mapVariationToDto(Variation variation) {
		return new VariationDto(variation.getId(), variation.getName());
	}

}
