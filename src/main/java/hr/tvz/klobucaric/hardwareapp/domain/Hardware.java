package hr.tvz.klobucaric.hardwareapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "HARDWARE")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "code"})
public class Hardware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "stocknumber")
    private Integer stock;
    private BigDecimal price;
    private String specification;
    @OneToMany(mappedBy = "hardware")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy="user")
    private Set<CartItem> cartItems = new HashSet<>();


    public enum Type{
        CPU,
        GPU,
        MBO,
        RAM,
        STORAGE,
        OTHER
    }

    public Hardware(String name, String code, String type, Integer stockNumber, BigDecimal price, String specification) {
        this.name = name;
        this.code = code;
        this.type = Type.valueOf(type);
        this.stock = stockNumber;
        this.price = price;
        this.specification = specification;
    }
}
