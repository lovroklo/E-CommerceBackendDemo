package hr.tvz.klobucaric.hardwareapp.servicesImpl;

import hr.tvz.klobucaric.hardwareapp.command.HardwareCommand;
import hr.tvz.klobucaric.hardwareapp.domain.Hardware;
import hr.tvz.klobucaric.hardwareapp.dto.HardwareDto;
import hr.tvz.klobucaric.hardwareapp.repository.HardwareRepository;
import hr.tvz.klobucaric.hardwareapp.services.HardwareService;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HardwareServiceImpl implements HardwareService {

    HardwareRepository hardwareRepository;

    public HardwareServiceImpl(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    @Override
    public List<HardwareDto> findAll() {
        return hardwareRepository.findAll().stream().map(this::mapHardwareToDTO).collect(Collectors.toList());

    }

    @Override
    public Optional<HardwareDto> findById(Long id) {
        return hardwareRepository.findById(id).map(this::mapHardwareToDTO);
    }

    @Override
    public Optional<HardwareDto> findByCode(final String code) {
        return hardwareRepository.findByCode(code).map(this::mapHardwareToDTO);
    }

    @Override
    public List<HardwareDto> findHardwaresByNameContainingIgnoreCase(String word) {
        return hardwareRepository.findHardwaresByNameContainingIgnoreCase(word).stream().map(this::mapHardwareToDTO).collect(Collectors.toList());
    }

    @Override
    public  List<HardwareDto> findHardwaresByType(String type) {
        Hardware.Type curType = Hardware.Type.valueOf(type);
        return hardwareRepository.findHardwaresByType(curType).stream().map(this::mapHardwareToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<Double> calculateAverageGrade(Long hardwareId) {

        Hardware hardware = hardwareRepository.findById(hardwareId).orElse(null);

        if (hardware==null||hardware.getReviews().isEmpty()) {
            return Optional.empty();
        }

        return hardwareRepository.calculateAverageGrade(hardwareId);
    }

    @Override
    public Optional<HardwareDto> save(final HardwareCommand hardwareCommand) {
        return Optional.of(mapHardwareToDTO(hardwareRepository.save(mapCommandToHardware(hardwareCommand))));
    }

    @Override
    public Optional<HardwareDto> update(String code, HardwareCommand updatedHardwareCommand) {
        Boolean exists = hardwareRepository.findByCode(code).isPresent();
        if(exists) {
            return Optional.of(mapHardwareToDTO(hardwareRepository.save(mapCommandToHardware(updatedHardwareCommand))));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public void deleteByCode(String code) {
        hardwareRepository.deleteHardwareByCode(code);
    }


    private Hardware mapCommandToHardware(final HardwareCommand hardwareCommand) {
        return new Hardware(hardwareCommand.getName(),hardwareCommand.getCode(), hardwareCommand.getType().toString(),
                hardwareCommand.getStock(), hardwareCommand.getPrice(), hardwareCommand.getSpecification());
    }

    private HardwareDto mapHardwareToDTO(final Hardware hardware){
        return new HardwareDto(hardware.getName(),hardware.getPrice().setScale(2, RoundingMode.HALF_UP), hardware.getCode(), hardware.getSpecification());
    }



}
