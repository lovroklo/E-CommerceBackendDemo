package hr.tvz.klobucaric.hardwareapp.controller;
import hr.tvz.klobucaric.hardwareapp.command.OrderCommand;
import hr.tvz.klobucaric.hardwareapp.dto.OrderDto;
import hr.tvz.klobucaric.hardwareapp.security.ApplicationUser;
import hr.tvz.klobucaric.hardwareapp.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Secured({"ROLE_ADMIN","ROLE_STAFF"})
    @GetMapping
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }


    @Secured({"ROLE_ADMIN","ROLE_STAFF"})
    @GetMapping(path = "/email/{email}")
    public List<OrderDto> getAllOrdersByEmail(@PathVariable final String email){
        return orderService.getOrdersByEmail(email);
    }

    @Secured({"ROLE_ADMIN","ROLE_STAFF"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void  deleteOrderById(@PathVariable final Long id){
         orderService.deleteOrdersById(id);
    }


    @Secured({"ROLE_USER"})
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody final OrderCommand orderCommand, @AuthenticationPrincipal ApplicationUser applicationUser){
        return orderService.createOrder(orderCommand, applicationUser.getUsername()).map(
                orderDto -> ResponseEntity.status(HttpStatus.CREATED).body(orderDto)
        ).orElseGet(
                () -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        );
    }



}
