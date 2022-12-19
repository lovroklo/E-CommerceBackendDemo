package hr.klobucaric.webshop.userReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

	@Query("""
        SELECT COUNT (o) > 0
        from OrderLine o
        WHERE(
            o.id = :orderLineId
            AND o.shopOrder.user.id = :userId
            AND  o.shopOrder.orderStatus = 'COMPLETED'
        )
    """)
	boolean userOrderedProduct(Long userId, Long orderLineId);
}