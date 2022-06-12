package hr.tvz.klobucaric.hardwareapp.controller;
import hr.tvz.klobucaric.hardwareapp.command.HardwareCommand;
import hr.tvz.klobucaric.hardwareapp.dto.HardwareDto;
import hr.tvz.klobucaric.hardwareapp.services.HardwareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/hardware")
@CrossOrigin(origins = "http://localhost:4200")
public class HardwareController {

    private final HardwareService hardwareService;
    public HardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }


    @GetMapping
    public List<HardwareDto> getAllHardwareComponents() {
        return hardwareService.findAll();
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<HardwareDto> getHardwareComponentByCode(@PathVariable final String code) {
        return hardwareService.findByCode(code).map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<HardwareDto> getHardwareComponentById(@PathVariable final Long id) {
        return hardwareService.findById(id).map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/grade/{id}")
    public ResponseEntity<Double> getAverageHardwareGrade(@PathVariable final Long id) {

        return hardwareService.calculateAverageGrade(id).map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/containing/{word}")
    public List<HardwareDto> getHardwaresByNameContainingAndIgnoreCase(@PathVariable final String word) {
        return hardwareService.findHardwaresByNameContainingIgnoreCase(word);
    }

    @GetMapping("/type/{type}")
    public List<HardwareDto> getHardwaresByType(@PathVariable final String type) {
        return hardwareService.findHardwaresByType(type);
    }

    @Secured({"ROLE_ADMIN","ROLE_STAFF"})
    @PostMapping
    public ResponseEntity<HardwareDto> save(@Valid @RequestBody final HardwareCommand hardwareCommand){
        return hardwareService.save(hardwareCommand)
                .map(
                        hardwareDto -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(hardwareDto)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }

    @Secured({"ROLE_ADMIN","ROLE_STAFF"})
    @PutMapping("/{code}")
    public ResponseEntity<HardwareDto> update(@PathVariable String code, @Valid @RequestBody final HardwareCommand hardwareCommand){
        return hardwareService.update(code, hardwareCommand)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @Secured({"ROLE_ADMIN","ROLE_STAFF"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    public void delete(@PathVariable String code){
        hardwareService.deleteByCode(code);
    }







}
