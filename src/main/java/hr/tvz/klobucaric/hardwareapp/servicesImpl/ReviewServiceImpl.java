package hr.tvz.klobucaric.hardwareapp.servicesImpl;

import hr.tvz.klobucaric.hardwareapp.command.ReviewCommand;
import hr.tvz.klobucaric.hardwareapp.command.UpdateReviewCommand;
import hr.tvz.klobucaric.hardwareapp.domain.Hardware;
import hr.tvz.klobucaric.hardwareapp.domain.Review;
import hr.tvz.klobucaric.hardwareapp.domain.User;
import hr.tvz.klobucaric.hardwareapp.dto.ReviewDto;
import hr.tvz.klobucaric.hardwareapp.repository.HardwareRepository;
import hr.tvz.klobucaric.hardwareapp.repository.ReviewRepository;
import hr.tvz.klobucaric.hardwareapp.repository.UserRepository;
import hr.tvz.klobucaric.hardwareapp.security.JwtFilter;
import hr.tvz.klobucaric.hardwareapp.security.SecurityUtils;
import hr.tvz.klobucaric.hardwareapp.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private HardwareRepository hardwareRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, HardwareRepository hardwareRepository, JwtFilter jwtFilter) {
        this.reviewRepository=reviewRepository;
        this.userRepository = userRepository;
        this.hardwareRepository = hardwareRepository;
    }
    @Override
    public List<ReviewDto> findAll() {
        return reviewRepository.findAll().stream().map(this::mapToReviewDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> findAllByHardwareCode(String code) {
        return reviewRepository.findAllByHardwareCode(code).stream().map(this::mapToReviewDTO).collect(Collectors.toList());
    }

    //Mogu izbrisati samo useri koji su ADMIN ili STAFF te ukoliko pripada tom useru review
    @Override
    public void deleteReviewById(Long id, String username) {
        if (!username.isEmpty()) {
            Review review = reviewRepository.findReviewById(id).get();
            User creator = userRepository.findByUsername(username).orElseGet(null);
            String authority = creator.getAuthorities().stream().findFirst().get().getName();
            if (review.getUser().getUsername().equals(username) || authority.equals("ROLE_ADMIN") || authority.equals("ROLE_STAFF")) {
                reviewRepository.deleteReviewById(id);
            }
        }
    }

    @Override
    public Optional<ReviewDto> save(ReviewCommand reviewCommand, String username) throws Exception {
        if(username.isEmpty()) {
            return Optional.empty();
        }
        User creator = userRepository.findByUsername(username).orElseGet(null);
        Hardware reviewedHardware = this.hardwareRepository.findById(reviewCommand.getHardwareId()).orElse(null);
        if(creator==null || reviewedHardware==null){
            return Optional.empty();
        }
        return Optional.of(
                mapToReviewDTO
                        (
                                reviewRepository.save(new Review(reviewCommand.getHeadline(),reviewCommand.getText(),
                                        reviewCommand.getReviewGrade(),reviewedHardware,creator))
                        )
        );
    }

    @Override
    public Optional<ReviewDto> update(UpdateReviewCommand updateReviewCommand, String username) {
        Review review = reviewRepository.findReviewById(updateReviewCommand.getId()).orElse(null);
        Boolean isValidUser = username.equals(review.getUser().getUsername());
        if(username.isEmpty() || review==null || !isValidUser){
            return Optional.empty();
        }
        review.setHeadline(updateReviewCommand.getHeadline());
        review.setText(updateReviewCommand.getText());
        review.setReviewGrade(updateReviewCommand.getReviewGrade());
        return Optional.of(
                mapToReviewDTO
                        (
                                reviewRepository.save(review)
                        )
        );

    }



    @Override
    public Optional<ReviewDto> findReviewById(Long id) {
        return reviewRepository.findReviewById(id).map(this::mapToReviewDTO);
    }
    public ReviewDto mapToReviewDTO(Review review){
        return new ReviewDto(review.getId(), review.getHeadline(),review.getText(),review.getReviewGrade(),
                review.getUser().getUsername(), review.getHardware().getCode(), review.getDateOfCreation());
    }
}
