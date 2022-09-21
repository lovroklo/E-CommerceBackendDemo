package hr.klobucaric.webshop.userReview;

import hr.klobucaric.webshop.orderLine.OrderLine;
import hr.klobucaric.webshop.orderLine.OrderLineRepository;
import hr.klobucaric.webshop.security.SecurityUtils;
import hr.klobucaric.webshop.user.UserRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReviewServiceImpl implements UserReviewService{

    private final UserReviewRepository userReviewRepository;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final OrderLineRepository orderLineRepository;

    @Override
    public UserReviewDto save(UserReviewCommand command) {
        try {
            UserReview userReview = mapCommandToUserReview(command);
            return mapUserReviewToDto(userReview);
        }catch (IllegalArgumentException e) {
            throw new ApiBadRequestException("Given order line is null." + e.getMessage());
        }catch (Exception e){
            throw new ApiBadRequestException("Something went wrong in service layer while saving the order line." + e.getMessage());
        }
    }

    private UserReviewDto mapUserReviewToDto(UserReview userReview) {
        return new UserReviewDto(userReview.getId(),userReview.getRatingValue(),userReview.getComment(),
                userReview.getUser().getEmail(),userReview.getOrderedProduct().getId());
    }

    private UserReview mapCommandToUserReview(UserReviewCommand command){
        UserReview userReview = new UserReview();
        userReview.setUser(userRepository.findByEmail(securityUtils.getAuthenticatedUser().getName()).orElseThrow(
                () -> new ApiBadRequestException("Authenticated user with is not found in database"))
        );
        OrderLine orderLine = orderLineRepository.findById(command.getOrderedProductId()).orElseThrow(
                () -> new ApiNotFoundException("There is no order line in database with id " + command.getOrderedProductId())
        );
        if(!userReviewRepository.userOrderedProduct(userReview.getUser().getId(), orderLine.getId())){
            throw new ApiBadRequestException("User can only post a review when his product has been delivered");
        }
        userReview.setOrderedProduct(orderLine);
        userReview.setComment(command.getComment());
        userReview.setRatingValue(command.getRatingValue());
        return userReview;
    }
}
