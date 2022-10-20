package hr.klobucaric.webshop.web;


import hr.klobucaric.webshop.category.CategoryCommand;
import hr.klobucaric.webshop.category.CategoryDto;
import hr.klobucaric.webshop.category.CategoryService;
import hr.klobucaric.webshop.product.ProductCommand;
import hr.klobucaric.webshop.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable final Long id){
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/parent")
    public ResponseEntity<List<CategoryDto>> getAllParentCategories(){
        return new ResponseEntity<>(categoryService.findByParentCategoryIsNull(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<CategoryDto> save(@Valid @RequestBody final CategoryCommand categoryCommand){
        return new ResponseEntity<>(categoryService.save(categoryCommand),HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id){
        categoryService.deleteById(id);
    }
}
