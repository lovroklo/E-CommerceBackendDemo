package hr.klobucaric.webshop.product;

import org.springframework.data.domain.Page;


public interface ProductService {

    Page<ProductDto> findAllByPagination(final Integer p, final Integer n);

    Page<ProductDto> findByCategoryId(Long id, Integer p, Integer n) ;

    ProductDto findById(Long id);

    ProductDto save(ProductCommand productCommand);

    ProductDto update(Long id, ProductCommand updatedProductCommand);

    void deleteById(Long id);
}
