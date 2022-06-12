package hr.tvz.klobucaric.hardwareapp.services;

import hr.tvz.klobucaric.hardwareapp.command.ReviewCommand;
import hr.tvz.klobucaric.hardwareapp.command.UpdateReviewCommand;
import hr.tvz.klobucaric.hardwareapp.dto.ReviewDto;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDto> findAll();
    List<ReviewDto> findAllByHardwareCode(String code);
    Optional<ReviewDto> findReviewById(Long id);
    void deleteReviewById(Long id, String username);
    Optional<ReviewDto> save(ReviewCommand reviewCommand, String username) throws Exception;
    Optional<ReviewDto> update(UpdateReviewCommand updateReviewCommand, String username);

}
