package hr.klobucaric.webshop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@Getter
public class CategoryCommand {

	@NotBlank
	private String name;

	private Long parentCategoryId;


}
