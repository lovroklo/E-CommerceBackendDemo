package hr.klobucaric.webshop.userReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

    @Query("""
        select count(o) > 0 from OrderLine o
        where o.id = :orderLineId and
        o.shopOrder.user.id = :userId
        and  o.shopOrder.orderStatus = 'COMPLETED'
    """)
    boolean userOrderedProduct(Long userId, Long orderLineId);
}