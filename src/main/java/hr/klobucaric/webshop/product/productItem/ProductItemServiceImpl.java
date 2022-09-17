package hr.klobucaric.webshop.product.productItem;

import hr.klobucaric.webshop.product.ProductRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
public class ProductItemServiceImpl implements ProductItemService{

    private final ProductItemRepository productItemRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductItemDto save(final ProductItemCommand productItemCommand) {
        try {
            ProductItem productItem = new ProductItem();
            mapCommandToProduct(productItem, productItemCommand);
            return mapProductItemToDto(productItemRepository.save(productItem));
        }catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given product is null." + e.getMessage());
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in service layer while saving the product." + e.getMessage());
        }
    }


    private void mapCommandToProduct(ProductItem productItem, final ProductItemCommand command) {
        productItem.setSKU(command.getSKU());
        productItem.setPrice(command.getPrice());
        productItem.setQtyInStock(command.getQtyInStock());
        productItem.setProductImage(command.getProductImage());
        productItem.setProduct(productRepository.findById(command.getProductId())
                .orElseThrow(()-> new ApiNotFoundException("There is no product with id: "+command.getProductId())));
    }

    private ProductItemDto mapProductItemToDto(ProductItem productItem){
        return new ProductItemDto(productItem.getId(), productItem.getSKU(), productItem.getQtyInStock(), productItem.getProductImage(),
                productItem.getPrice().setScale(2, RoundingMode.HALF_UP), productItem.getProduct().getName(), productItem.getProduct().getDescription());
    }
}
