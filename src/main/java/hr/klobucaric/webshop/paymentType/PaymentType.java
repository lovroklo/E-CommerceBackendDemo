package hr.klobucaric.webshop.paymentType;

import hr.klobucaric.webshop.paymentMethod.PaymentMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private String value;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "paymentType")
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

}