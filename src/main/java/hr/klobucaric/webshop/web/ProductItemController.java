package hr.klobucaric.webshop.web;

import hr.klobucaric.webshop.productItem.ProductItemCommand;
import hr.klobucaric.webshop.productItem.ProductItemDto;
import hr.klobucaric.webshop.productItem.ProductItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-items")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductItemController {

    private final ProductItemService productItemService;

    @PostMapping
    public ResponseEntity<ProductItemDto> save(@Valid @RequestBody final ProductItemCommand productItemCommand){
        return new ResponseEntity<>(productItemService.save(productItemCommand), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProductItemDto> findByVariationOptions(@RequestParam(value = "productId") Long productId,
                                                                 @RequestParam(value = "variationsId") Long... variationsIdStr){
        Set<Long> variationOptionIdSet= new HashSet<>(Arrays.asList(variationsIdStr));
        return new ResponseEntity<>(productItemService.findByVariationOptions(productId, variationOptionIdSet), HttpStatus.OK);
    }
}
