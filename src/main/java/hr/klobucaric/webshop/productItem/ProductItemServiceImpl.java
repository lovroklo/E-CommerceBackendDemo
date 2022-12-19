package hr.klobucaric.webshop.productItem;

import hr.klobucaric.webshop.product.ProductRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import hr.klobucaric.webshop.variationOption.VariationOption;
import hr.klobucaric.webshop.variationOption.VariationOptionDto;
import hr.klobucaric.webshop.variationOption.VariationOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductItemServiceImpl implements ProductItemService {

	private final ProductItemRepository productItemRepository;
	private final ProductRepository productRepository;
	private final VariationOptionRepository variationOptionRepository;

	@Override
	public ProductItemDto save(final ProductItemCommand productItemCommand) {
		try {
			ProductItem productItem = mapCommandToProduct(productItemCommand);
			return mapProductItemToDto(productItemRepository.save(productItem));
		} catch (IllegalArgumentException e) {
			throw new ApiBadRequestException("Given product is null." + e.getMessage());
		} catch (Exception e) {
			throw new ApiBadRequestException("Something went wrong in service layer while saving the product - " + e);
		}
	}

	@Override
	public ProductItemDto findByVariationOptions(Long productId, Set<Long> variationOptionIdSet) {
		Set<VariationOption> variationOptions = variationOptionRepository.findAllByIdIn(variationOptionIdSet);
		if(variationOptions.size()!=variationOptionIdSet.size()){
			throw new ApiBadRequestException("At least one wrong id in parameter variationOptionsId");
		}
		String prodConfStr = "";
		List<Long> sortedIds = variationOptionIdSet.stream().sorted().toList();
		for (Long id: sortedIds) {
			prodConfStr = prodConfStr.concat(id+",");
		}
		prodConfStr = prodConfStr.substring(0,prodConfStr.length()-1);

		return mapProductItemToDto(productItemRepository.findByProduct_IdAndProdConfStr(productId,prodConfStr)
				.orElseThrow(
						() -> new ApiNotFoundException("There is no product item with selected parameters")
				)
		);
	}

	private ProductItem mapCommandToProduct(final ProductItemCommand command) {
		ProductItem productItem = new ProductItem();
		String prodConfStr = "";
		productItem.setProduct(productRepository.findById(command.getProductId()).orElseThrow(
				()-> new ApiNotFoundException("There is no product with id: "+command.getProductId()))
		);
		Set<VariationOption> variationOptions = new HashSet<>();
		List <Long> ids = command.getVariationOptionsId().stream().sorted().collect(Collectors.toList());
		for (Long id: ids) {
			variationOptions.add( variationOptionRepository.findById(id).orElseThrow(
					() -> new ApiNotFoundException("There is no variation option with id: "+ id))
			);
			prodConfStr = prodConfStr.concat(id+",");
		}
		prodConfStr = prodConfStr.substring(0,prodConfStr.length()-1);
		if(productItemRepository.existsByProdConfStr(prodConfStr)) {
			throw new ApiBadRequestException("There is already a product in database with selected variation options");
		}
		productItem.setVariationOptions(variationOptions);
		productItem.setProdConfStr(prodConfStr);
		productItem.setSKU(command.getSKU());
		productItem.setPrice(command.getPrice());
		productItem.setQtyInStock(command.getQtyInStock());
		productItem.setProductImage(command.getProductImage());
		return productItem;
	}

	private ProductItemDto mapProductItemToDto(ProductItem productItem) {
		return new ProductItemDto(
				productItem.getId(),
				productItem.getSKU(),
				productItem.getQtyInStock(),
				productItem.getProductImage(),
				productItem.getPrice().setScale(2, RoundingMode.HALF_UP),
				productItem.getProduct().getName(),
				productItem.getProduct().getDescription(),
				productItem.getVariationOptions().stream().map(this::mapVariationOptionToDto).collect(Collectors.toSet())
		);
	}

	private VariationOptionDto mapVariationOptionToDto(VariationOption variationOption) {
		return new VariationOptionDto(
				variationOption.getId(),
				variationOption.getVariation().getId(),
				variationOption.getValue());
	}
}
