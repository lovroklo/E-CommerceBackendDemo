package hr.klobucaric.webshop.paymentMethod;

import hr.klobucaric.webshop.paymentType.PaymentTypeRepository;
import hr.klobucaric.webshop.security.SecurityUtils;
import hr.klobucaric.webshop.user.UserRepository;
import hr.klobucaric.webshop.utils.exception.ApiBadRequestException;
import hr.klobucaric.webshop.utils.exception.ApiNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

	private final PaymentMethodRepository paymentMethodRepository;
	private final SecurityUtils securityUtils;
	private final UserRepository userRepository;
	private final PaymentTypeRepository paymentTypeRepository;

	@Override
	public PaymentMethodDto save(PaymentMethodCommand command) {
		try {
			return mapPaymentMethodToDto(paymentMethodRepository.save(mapCommandToPaymentMethod(command)));
		} catch (IllegalArgumentException e) {
			throw new ApiBadRequestException("Given payment method is null." + e.getMessage());
		} catch (Exception e){
			throw new ApiBadRequestException("Something went wrong in service layer while saving the payment method." + e.getMessage());
		}
	}

	//todo implement different paymentDto objects based on payment type
	private PaymentMethodDto mapPaymentMethodToDto(PaymentMethod paymentMethod) {
		return new PaymentMethodDto(
				paymentMethod.getId(),
				paymentMethod.getProvider(),
				paymentMethod.getAccountNumber(),
				paymentMethod.getExpiryDate(),
				paymentMethod.getUser().getId(),
				paymentMethod.getPaymentType().getId()
		);
	}

	// TODO: 9/19/2022 find a better way for using payment type
	private PaymentMethod mapCommandToPaymentMethod(PaymentMethodCommand command) {
		PaymentMethod paymentMethod = new PaymentMethod();
		// TODO: 9/19/2022 find out if I need to use exception with getAuthenticatedUser or not
		paymentMethod.setUser(userRepository.findByEmail(securityUtils.getAuthenticatedUser().getName()).orElseThrow(
					() -> new ApiBadRequestException("Authenticated user is not in database")
				)
		);

		paymentMethod.setPaymentType(paymentTypeRepository.findById(command.getPaymentTypeId()).orElseThrow(
					() -> new ApiNotFoundException("Provided payment type id is not found in database!")
				)
		);

		// TODO: 9/21/2022 implement different payment types!
		//if(paymentMethod.getPaymentType().getValue()!="Pouzeće"){
		paymentMethod.setProvider(command.getProvider());
		paymentMethod.setAccountNumber(command.getAccountNumber());
		paymentMethod.setExpiryDate(command.getExpiryDate());
		// }

		return paymentMethod;
	}


}
