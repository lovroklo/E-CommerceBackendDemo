package hr.klobucaric.webshop.paymentType;

import hr.klobucaric.webshop.paymentMethod.PaymentMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payment_type")
@Getter
@Setter
@NoArgsConstructor
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Type can't be blank!")
    @Column(name = "value", unique = true, nullable = false)
    private String value;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "paymentType")
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

}