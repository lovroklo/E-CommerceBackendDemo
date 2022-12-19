package hr.klobucaric.webshop.userReview;

import java.io.Serializable;

public record UserReviewDto(Long id, Integer ratingValue, String comment, String firstName, Long orderedProductId) implements Serializable {
}
