package hr.klobucaric.webshop.userReview;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class UserReviewCommand {

	@NotNull
	private Long orderedProductId;

	@Min(1)
	@Max(5)
	@NotNull
	private Integer ratingValue;

	@NotBlank
	private String comment;

}
