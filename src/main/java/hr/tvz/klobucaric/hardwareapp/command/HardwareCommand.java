package hr.tvz.klobucaric.hardwareapp.command;
import hr.tvz.klobucaric.hardwareapp.domain.Hardware;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class HardwareCommand {
    @NotBlank(message = "Name can't be empty")
    private String name;
    @NotBlank(message = "Code can't be empty")
    private String code;
    @NotNull(message = "Type of hardware can't be null")
    private Hardware.Type type;
    @PositiveOrZero(message = "Number of components in stock should always be positive or 0")
    private Integer stock;
    @Positive(message = "Price should always be positive")
    private BigDecimal price;
    @NotBlank(message = "Specification can't be empty")
    private String specification;

    public HardwareCommand( @NotBlank(message = "Name can't be empty") String name,
    @NotBlank(message = "Code can't be empty") String code,
    @NotNull(message = "Type of hardware can't be null") Hardware.Type type,
    @PositiveOrZero(message = "Number of components in stock should always be positive or 0") Integer stock,
    @Positive(message = "Price should always be positive") BigDecimal price,
    @NotBlank(message = "Specification can't be empty") String specification) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.specification =specification;
    }


}
