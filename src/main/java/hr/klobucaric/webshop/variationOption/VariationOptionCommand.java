package hr.klobucaric.webshop.variationOption;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class VariationOptionCommand {

    @NotNull(message = "Variation id can't be null!")
    private Long variationId;

    @NotBlank(message = "Variation value can't be empty or null!")
    private String value;

}
