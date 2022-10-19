package hr.klobucaric.webshop.category;

import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNoContentException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryDto> findByParentCategoryIsNull() {
        List<CategoryDto> categoryDtos;
        try{
            categoryDtos = categoryRepository.findCategoryDtosByParentCategoryIsNull();
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in Service layer while fetching all parent categories" + e.getMessage());
        }
        if(categoryDtos.isEmpty()){
            throw new ApiNoContentException("There are no categories in database");
        }
        return categoryDtos;
    }

    @Override
    public CategoryDto findById(Long id) {
        try {
            return categoryRepository.findCategoryDtoById(id);
        } catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given category id is null, please send some id to be searched." + e.getMessage());
        } catch (NoSuchElementException e){
            throw new ApiNotFoundException("Category with id="+id+" does not exist in database. " + e.getMessage());
        } catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in Service layer while fetching category by id. " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public CategoryDto save(CategoryCommand command) {
        try {
            Category category;
            Boolean categoryParentIdIsNull = command.getParentCategoryId().equals(null);
            if(categoryParentIdIsNull){
                category = new Category(command.getName(),null);
            }else {
                Category parentCategory = categoryRepository.findById(command.getParentCategoryId())
                        .orElseThrow(() -> new ApiBadRequestException(
                                "There is no parent category with id: "+ command.getParentCategoryId()));
                category = new Category(command.getName(), parentCategory);
            }
            category = categoryRepository.save(category);
            if(categoryParentIdIsNull){
                category.setPath(category.getId().toString());
            }else {
                category.setPath(category.getParentCategory().getPath()+"."+category.getId());
            }
            return mapCategoryToDto(category);
        }catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given category is null." + e.getMessage());
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in service layer while saving the category." + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ApiNotFoundException("There is no category with id" + id)
        );

        if (!category.getChildCategories().isEmpty()){
            throw new ApiBadRequestException("Category with id: "+ id + " can't be deleted unless all of its subcategories are deleted!");
        }

        category.getProducts().stream().forEach(product ->
            product.setCategory(category.getParentCategory())
        );

        log.info("Successfully deleted category and changed id to parent category on every product that was a part of it");
        categoryRepository.deleteById(id);

    }

    private CategoryDto mapCategoryToDto(Category category){
        return new CategoryDto(category.getId(), category.getName());
    }


}
