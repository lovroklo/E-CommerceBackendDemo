package hr.klobucaric.webshop.web;

import hr.klobucaric.webshop.address.AddressCommand;
import hr.klobucaric.webshop.address.AddressDto;
import hr.klobucaric.webshop.address.AddressService;
import hr.klobucaric.webshop.user.LoginCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {

	private final AddressService addressService;

	@PostMapping
	public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressCommand command) {
		return new ResponseEntity<>(addressService.createAddress(command), HttpStatus.CREATED);
	}

}
