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

        try{
            productDtoList = productRepository.findProductDtoPage(PageRequest.of(p, n, Sort.by("name")));
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in Service layer while fetching all products" + e.getMessage());
        }
        if(productDtoList.getContent().isEmpty()){
            throw new ApiNoContentException("Product component list is empty, there is nothing to return");
        }
        return productDtoList;
    }

    @Override
    public  Page<ProductDto> findByCategoryId(Long id, Integer p, Integer n)  {
        final Page<ProductDto> productDtoList;
        try{
            productDtoList = productRepository.findProductDtoPageByCategoryId(PageRequest.of(p, n, Sort.by("name")),id);
        }catch (IllegalArgumentException e){
          throw new ApiBadRequestException("Provided product category id is not in database! " +e.getMessage());
        } catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in Service layer while fetching all products" + e.getMessage());
        }
        if(productDtoList.getContent().isEmpty()){
            throw new ApiNotFoundException("There is no product found in database by selected parameters");
        }
        return productDtoList;
    }

    @Override
    public ProductDto findById(Long id) {
        try {
            return productRepository.findOneProductDtoById(id).get();
        } catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given product id is null, please send some id to be searched." + e.getMessage());
        } catch (NoSuchElementException e){
            throw new ApiNotFoundException("Product component with id="+id+" does not exist in database. " + e.getMessage());
        } catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in Service layer while fetching product by id. " + e.getMessage());
        }
    }
    @Override
    public ProductDto save(final ProductCommand productCommand) {
        try {
            Product product = new Product();
            mapCommandToProduct(product,productCommand);
            product.setCategory(categoryRepository.findById(productCommand.getCategoryId()).orElseThrow(ApiNotFoundException::new));
            return mapProductToDTO(productRepository.save(product));
        }catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given product is null." + e.getMessage());
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in service layer while saving the product." + e.getMessage());
        }
    }

    @Transactional
    @Override
    public ProductDto update(Long id, ProductCommand updatedProductCommand) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ApiNotFoundException("There is no product by given id which is " + id);
        }
        try{
            mapCommandToProduct(product.get(),updatedProductCommand);
            return mapProductToDTO(product.get());
        }catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given product is null." + e.getMessage());
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in service layer while saving the product component." + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            Long numberOfDeletedProducts = productRepository.deleteProductById(id);
            if(numberOfDeletedProducts<1)
                throw new ApiNotFoundException("There is no product by given id which is " + id);
        }catch (IllegalArgumentException e){
            throw new ApiBadRequestException("Given product id is null, please send correct id. " + e.getMessage());
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in service layer while deleting by id. " + e.getMessage());
        }
    }

    private void mapCommandToProduct(Product product, final ProductCommand productCommand) {
        product.setName(productCommand.getName());
        product.setDescription(productCommand.getDescription());
        product.setProductImage(productCommand.getProductImage());

    }

    private ProductDto mapProductToDTO(final Product product){
        return new ProductDto(product.getId(),product.getName(),
                product.getDescription(), product.getProductImage());
    }



}
