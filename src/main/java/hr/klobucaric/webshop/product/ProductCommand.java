package hr.klobucaric.webshop.product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ProductCommand {

	@NotBlank(message = "Name can't be empty")
	private String name;

	@NotBlank(message = "Description can't be empty")
	private String description;

	@URL
	private String productImage;

	@NotNull(message = "Category id can't be null")
	private Long categoryId;

}
