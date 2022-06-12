package hr.tvz.klobucaric.hardwareapp.repository;
import hr.tvz.klobucaric.hardwareapp.domain.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HardwareRepository extends JpaRepository<Hardware,Long> {

    Optional<Hardware> findById(Long id);

    Optional<Hardware> findByCode(String code);

    List<Hardware> findHardwaresByNameContainingIgnoreCase(String name);

    List<Hardware> findHardwaresByType (Hardware.Type type);

    @Query("SELECT AVG(r.reviewGrade) FROM Review r " +
            "WHERE r.hardware.id=?1")
    Optional<Double> calculateAverageGrade(Long hardwareId);

    void deleteHardwareByCode(String code);




}
