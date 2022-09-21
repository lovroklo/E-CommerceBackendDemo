package hr.klobucaric.webshop.product;

import hr.klobucaric.webshop.category.CategoryRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNoContentException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;


@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    @Override
    public Page<ProductDto> findAllByPagination(Integer p, Integer n) {
        Page<ProductDto> productDtoList;
        switch (n){
            case 12:
            case 24:
            case 48:
                break;
            default: n=12;
        }
        productDtoList = productRepository.findProductDtoPage(PageRequest.of(p, n, Sort.by("name")));
        if(productDtoList.getContent().isEmpty()){
            throw new ApiNoContentException("Product component list is empty, there is nothing to return");
        }
        return productDtoList;
    }

    @Override
    public  Page<ProductDto> findByCategoryId(Long id, Integer p, Integer n)  {
        final Page<ProductDto> productDtoList;
        productDtoList = productRepository.findProductDtoPageByCategoryId(PageRequest.of(p, n, Sort.by("name")),id);
        if(productDtoList.getContent().isEmpty()){
           throw new ApiNotFoundException("There is no product found in database by selected parameters");
        }
        return productDtoList;
    }

    @Override
    public ProductDto findById(Long id) {
        try {
            return productRepository.findOneProductDtoById(id).orElseThrow(
                    () -> new ApiNotFoundException("Product component with id="+id+" does not exist in database!")
            );
        } catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given product id is null, please send some id to be searched." + e.getMessage());
        }
    }

    @Override
    public ProductDto save(final ProductCommand productCommand) {
        Product product = mapCommandToProduct(productCommand);
        product.setCategory(categoryRepository.findById(productCommand.getCategoryId()).orElseThrow(
                () -> new ApiNotFoundException("There is no category in database with id=" + productCommand.getCategoryId())
        ));
        return mapProductToDTO(productRepository.save(product));
    }

    @Transactional
    @Override
    public ProductDto update(Long id, ProductCommand command) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ApiNotFoundException("There is no product with id "+ id + " in database")
        );
        product.setCategory(categoryRepository.findById(command.getCategoryId()).orElseThrow(
                () -> new ApiNotFoundException("There is no category in database with id=" + command.getCategoryId())
        ));
        product.setName(command.getName());
        product.setDescription(command.getDescription());
        product.setProductImage(command.getProductImage());
        return mapProductToDTO(product);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Long numberOfDeletedProducts = productRepository.deleteProductById(id);
        if(numberOfDeletedProducts<1)
            throw new ApiNotFoundException("There is no product by given id which is " + id);
    }

    private Product mapCommandToProduct(final ProductCommand productCommand) {
        Product product = new Product();
        product.setName(productCommand.getName());
        product.setDescription(productCommand.getDescription());
        product.setProductImage(productCommand.getProductImage());
        return product;
    }


    private ProductDto mapProductToDTO(final Product product){
        return new ProductDto(product.getId(),product.getName(),
                product.getDescription(), product.getProductImage(),
                product.getCategory().getPath());
    }



}
