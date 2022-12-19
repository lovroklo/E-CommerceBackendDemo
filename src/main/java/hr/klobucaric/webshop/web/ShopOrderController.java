package hr.klobucaric.webshop.web;


import hr.klobucaric.webshop.shopOrder.ShopOrderCommand;
import hr.klobucaric.webshop.shopOrder.ShopOrderDto;
import hr.klobucaric.webshop.shopOrder.ShopOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class ShopOrderController {

	private final ShopOrderService shopOrderService;

	@PostMapping
	public ResponseEntity<ShopOrderDto> saveOrder(@Valid @RequestBody final ShopOrderCommand command) {
		return new ResponseEntity<>(shopOrderService.placeOrder(command), HttpStatus.CREATED);
	}



}
