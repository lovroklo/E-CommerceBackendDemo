package hr.klobucaric.webshop.category;


import java.util.List;

public interface CategoryService {

     CategoryDto findById(Long id);
     CategoryDto save(CategoryCommand command);
     List<CategoryDto> findByParentCategoryIsNull();
}
