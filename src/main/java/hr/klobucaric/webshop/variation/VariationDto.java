package hr.klobucaric.webshop.variation;
import java.io.Serializable;

public record VariationDto(Long id, String name) implements Serializable {
}
