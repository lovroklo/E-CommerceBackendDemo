package hr.tvz.klobucaric.hardwareapp.services;
import hr.tvz.klobucaric.hardwareapp.command.HardwareCommand;
import hr.tvz.klobucaric.hardwareapp.dto.HardwareDto;

import java.util.List;
import java.util.Optional;

public interface HardwareService {

    List<HardwareDto> findAll();

    Optional<HardwareDto> findById(Long id);

    Optional <HardwareDto> findByCode(String code);


    List<HardwareDto> findHardwaresByNameContainingIgnoreCase(String name);

    List<HardwareDto> findHardwaresByType (String type);

    Optional<Double> calculateAverageGrade(Long hardwareId);

    Optional <HardwareDto> save(HardwareCommand hardwareCommand);

    Optional<HardwareDto> update(String code, HardwareCommand updatedHardwareCommand);

    void deleteByCode (String code);
}
