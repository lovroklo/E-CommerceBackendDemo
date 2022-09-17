package hr.klobucaric.webshop.product;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<ProductDto> findAllByPagination(final Integer p, final Integer n);

    ProductDto findById(Long id);

    List<ProductDto> findByCategoryId (Long id);

    ProductDto save(ProductCommand productCommand);

    ProductDto update(Long id, ProductCommand updatedProductCommand);

    void deleteById(Long id);
}
