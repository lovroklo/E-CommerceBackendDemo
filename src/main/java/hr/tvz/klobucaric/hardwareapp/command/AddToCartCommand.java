package hr.tvz.klobucaric.hardwareapp.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AddToCartCommand {

    @NotNull
    private Long hardwareId;
    @Positive
    private Integer quantity;


    public AddToCartCommand(Long hardwareId, Integer quantity) {
        this.hardwareId = hardwareId;
        this.quantity = quantity;
    }

}
