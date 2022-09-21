package hr.klobucaric.webshop.variationOption;

import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import hr.klobucaric.webshop.variation.VariationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VariationOptionServiceImpl implements VariationOptionService{

    private final VariationOptionRepository variationOptionRepository;
    private final VariationRepository variationRepository;

    @Override
    public Set<VariationOptionDto> findVariationOptionsByVariationId(Long id) {
        final Set<VariationOptionDto> variationOptionDtos;
        try{
            variationOptionDtos = variationOptionRepository.findVariationDtoSetByVariationId(id);
        }catch (IllegalArgumentException e){
            throw new ApiBadRequestException("Provided variation id is not in database! " +e.getMessage());
        } catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in Service layer while fetching all variation options by vartiation id" + e.getMessage());
        }
        if(variationOptionDtos.isEmpty()){
            throw new ApiNotFoundException("There is no variation option found in database by selected parameters");
        }
        return variationOptionDtos;
    }

    @Override
    public VariationOptionDto findVariationOptionById(Long id) {
        try {
            return variationOptionRepository.findDtoById(id);
        } catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given variation option id is null, please send some id to be searched." + e.getMessage());
        } catch (NoSuchElementException e){
            throw new ApiNotFoundException("Variation option with id="+id+" does not exist in database. " + e.getMessage());
        } catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in Service layer while fetching variation option by id. " + e.getMessage());
        }
    }

    @Override
    public VariationOptionDto save(VariationOptionCommand command) {
        try {
            VariationOption variationOption = new VariationOption();
            variationOption.setVariation(
                    variationRepository.findById(command.getVariationId()).orElseThrow(
                            () -> new ApiNotFoundException("There is no category with id: "+ command.getVariationId())
                    ));
            variationOption.setValue(command.getValue());
            return mapVariationOptionToDto(variationOptionRepository.save(variationOption));
        }catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given variation is null." + e.getMessage());
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in service layer while saving the variation." + e.getMessage());
        }
    }

    private VariationOptionDto mapVariationOptionToDto(VariationOption variationOption){
        return new VariationOptionDto(variationOption.getId(), variationOption.getVariation().getId(), variationOption.getValue());
    }
}
