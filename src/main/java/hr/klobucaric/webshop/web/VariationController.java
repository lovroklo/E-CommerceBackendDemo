package hr.klobucaric.webshop.web;

import hr.klobucaric.webshop.variation.VariationCommand;
import hr.klobucaric.webshop.variation.VariationDto;
import hr.klobucaric.webshop.variation.VariationService;
import hr.klobucaric.webshop.variationOption.VariationOptionCommand;
import hr.klobucaric.webshop.variationOption.VariationOptionDto;
import hr.klobucaric.webshop.variationOption.VariationOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/variations")
public class VariationController {

    private final VariationService variationService;
    private final VariationOptionService variationOptionService;

    @GetMapping("/{id}")
    public ResponseEntity<VariationDto> getVariationById(@PathVariable final Long id) {
        return new ResponseEntity<>(variationService.findVariationById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/categories/{id}")
    public ResponseEntity<Set<VariationDto>> getAllVariationsByCategoryId(@PathVariable final Long id) {
        return new ResponseEntity<>(variationService.findVariationsByCategoryId(id), HttpStatus.OK);
    }

    @GetMapping("-option/id/{id}")
    public ResponseEntity<VariationOptionDto> getVariationOptionById(@PathVariable final Long id) {
        return new ResponseEntity<>(variationOptionService.findVariationOptionById(id), HttpStatus.OK);
    }

    @GetMapping("-option/ctg/{id}")
    public ResponseEntity<Set<VariationOptionDto>> getAllVariationOptionsByVariationId(@PathVariable final Long id) {
        return new ResponseEntity<>(variationOptionService.findVariationOptionsByVariationId(id), HttpStatus.OK);
    }

    @PostMapping(value = "-option")
    public ResponseEntity<VariationOptionDto> saveVariationOption(@Valid @RequestBody final VariationOptionCommand command){
        return new ResponseEntity<>(variationOptionService.save(command),HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<VariationDto> saveVariation(@Valid @RequestBody final VariationCommand command){
        return new ResponseEntity<>(variationService.save(command),HttpStatus.CREATED);
    }

}
