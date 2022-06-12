package hr.tvz.klobucaric.hardwareapp.repository;

import hr.tvz.klobucaric.hardwareapp.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByHardwareCode(String code);

    Optional<Review> findReviewById(Long id);

    void deleteReviewById(Long id);


    }
