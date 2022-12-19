package hr.klobucaric.webshop.paymentMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class PaymentMethodCommand {

	private Long paymentTypeId;

	private String provider;

	private String accountNumber;

	private Date expiryDate;

}
