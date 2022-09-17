package hr.klobucaric.webshop.category;

import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNoContentException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
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

    //todo only superAdmin can delete level 0 category (with no parents)
    //todo implement deleting subcategories, products then move to higher category

    //Not the most optimized solution, this function will not be used often anyway
    @Transactional
    @Override
    public CategoryDto save(CategoryCommand command) {
        try {
            Category category;

            if(command.getParentCategoryId()==null){
                category = new Category();
                category.setName(command.getName());
                category.setParentCategory(null);
            }else {
                Category parentCategory = categoryRepository.findById(command.getParentCategoryId())
                        .orElseThrow(() -> new ApiBadRequestException(
                                "There is no parent category with id: "+ command.getParentCategoryId()));
                category = new Category(command.getName(), parentCategory);
            }
            category.setPath("");
            category = categoryRepository.save(category);
            String path="";
            Category tempCategory = category;
            while (true){
                StringBuffer tempId = new StringBuffer(tempCategory.getId().toString());
                if(tempCategory.getParentCategory()!=null) {
                    path += tempId.reverse()+ ".";
                    tempCategory=tempCategory.getParentCategory();
                }
                else {
                    path += tempId.reverse();
                    break;
                }
            }
            StringBuffer sbr = new StringBuffer(path);
            path = sbr.reverse().toString();
            category.setPath(path);
            return mapCategoryToDto(category);
        }catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given category is null." + e.getMessage());
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in service layer while saving the category." + e.getMessage());
        }
    }

    private CategoryDto mapCategoryToDto(Category category){
        return new CategoryDto(category.getId(), category.getName());
    }


}
