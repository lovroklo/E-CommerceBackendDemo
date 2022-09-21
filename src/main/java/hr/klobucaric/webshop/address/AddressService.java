package hr.klobucaric.webshop.address;


public interface AddressService {

    AddressDto createAddress(AddressCommand addressCommand);
    void setDefaultAddress(Long id);
}
