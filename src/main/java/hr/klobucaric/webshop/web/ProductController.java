package hr.klobucaric.webshop.web;
import hr.klobucaric.webshop.product.ProductDto;
import hr.klobucaric.webshop.product.ProductCommand;
import hr.klobucaric.webshop.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllProducts(@RequestParam(name = "p", defaultValue = "0")  Integer pageNumber,
                                                           @RequestParam(name = "numberOfProducts", defaultValue = "12")  Integer numberOfProducts) {
        Page<ProductDto> productPage = productService.findAllByPagination(pageNumber, numberOfProducts);

        Map<String, Object> response = new HashMap<>();
        response.put("products", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable final Long id) {
        return new ResponseEntity<>(productService.findById(id),HttpStatus.OK);
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductByCategoryId(@PathVariable final Long id) {
        return new ResponseEntity<>(productService.findByCategoryId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> save(@Valid @RequestBody final ProductCommand productCommand){
        return new ResponseEntity<>(productService.save(productCommand),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody final ProductCommand productCommand){
        return new ResponseEntity<>(productService.update(id, productCommand), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteByCode(@PathVariable Long id){
        productService.deleteById(id);
    }







}
