package hr.tvz.klobucaric.hardwareapp.controller;
import hr.tvz.klobucaric.hardwareapp.command.ReviewCommand;
import hr.tvz.klobucaric.hardwareapp.command.UpdateReviewCommand;
import hr.tvz.klobucaric.hardwareapp.dto.ReviewDto;
import hr.tvz.klobucaric.hardwareapp.security.ApplicationUser;
import hr.tvz.klobucaric.hardwareapp.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDto>getAllReviews(){return reviewService.findAll();}

    @GetMapping(path = "/code/{code}")
    public List<ReviewDto> getReviewsByHardwareCode(@PathVariable final String code){
        return reviewService.findAllByHardwareCode(code);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable final Long id) {
        return reviewService.findReviewById(id).map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody final ReviewCommand reviewCommand, @AuthenticationPrincipal ApplicationUser userDetails) throws Exception {
        return reviewService.save(reviewCommand, userDetails.getUsername())
                .map(
                        reviewDto -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(reviewDto)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @Secured("ROLE_USER")
    @PutMapping
    public ResponseEntity<ReviewDto> updateReview(@Valid @RequestBody final UpdateReviewCommand updateReviewCommand, @AuthenticationPrincipal ApplicationUser userDetails) {
        return reviewService.update(updateReviewCommand, userDetails.getUsername())
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @Secured({"ROLE_ADMIN","ROLE_STAFF", "ROLE_USER"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal ApplicationUser userDetails){
        reviewService.deleteReviewById(id, userDetails.getUsername());
    }

}
