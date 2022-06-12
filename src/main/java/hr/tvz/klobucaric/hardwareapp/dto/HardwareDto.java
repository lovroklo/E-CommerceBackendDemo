package hr.tvz.klobucaric.hardwareapp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"code"})
public class HardwareDto {

    private String name, code, specification;
    private BigDecimal price;

    public HardwareDto(String name, BigDecimal price, String code, String specification) {
        this.name = name;
        this.price = price;
        this.code = code;
        this.specification = specification;
    }


}
