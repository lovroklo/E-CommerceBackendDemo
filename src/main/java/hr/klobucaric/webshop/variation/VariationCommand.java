package hr.klobucaric.webshop.variation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class VariationCommand {

    @NotNull(message = "Category id can't be null!")
    private Long categoryId;

    @NotBlank(message = "Name can't be empty")
    private String name;

}
