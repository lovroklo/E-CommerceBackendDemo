package hr.klobucaric.webshop.paymentMethod;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}