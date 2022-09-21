package hr.klobucaric.webshop.paymentMethod;

import java.io.Serializable;
import java.util.Date;

public record PaymentMethodDto(Long id, String provider, String accountNumber, Date expiryDate,
                               Long userId, Long paymentTypeId) implements Serializable {
}
