package hr.klobucaric.webshop.category;


import java.util.List;

public interface CategoryService {

	CategoryDto findById(Long id);

	CategoryDto save(CategoryCommand command);

	void deleteById(Long id);

	List<CategoryDto> findByParentCategoryIsNull();
}
