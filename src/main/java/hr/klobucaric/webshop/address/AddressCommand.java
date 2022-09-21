package hr.klobucaric.webshop.address;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class AddressCommand {

    @NotBlank
    private String city;

    @NotNull
    @Positive(message = "Postal code must be a positive number")
    private Integer postalCode;

    @NotBlank
    private String addressLine;

    @Positive(message = "Street number must be a positive number")
    private Integer streetNumber;


    private Boolean isDefault;

    public Boolean getIsDefault(){
        if(this.isDefault==null)
            return false;
        else return this.isDefault;
    }
}
