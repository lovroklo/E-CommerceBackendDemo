package hr.klobucaric.webshop.userReview;

import hr.klobucaric.webshop.orderLine.OrderLine;
import hr.klobucaric.webshop.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_review")
@Getter
@Setter
@NoArgsConstructor
public class UserReview {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private Integer ratingValue;
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordered_product_id")
	private OrderLine orderedProduct;

}