package hr.klobucaric.webshop.web;

import hr.klobucaric.webshop.product.productItem.ProductItemCommand;
import hr.klobucaric.webshop.product.productItem.ProductItemDto;
import hr.klobucaric.webshop.product.productItem.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-item/")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductItemController {

    private final ProductItemService productItemService;

    @PostMapping
    public ResponseEntity<ProductItemDto> save(@Valid @RequestBody final ProductItemCommand productItemCommand){
        return new ResponseEntity<>(productItemService.save(productItemCommand), HttpStatus.CREATED);
    }

}
