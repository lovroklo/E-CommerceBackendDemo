package hr.klobucaric.webshop.address;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddressDto {

    private Integer id;
    private String city;
    private Integer postalCode;
    private String addressLine;
    private Integer streetNumber;

}
