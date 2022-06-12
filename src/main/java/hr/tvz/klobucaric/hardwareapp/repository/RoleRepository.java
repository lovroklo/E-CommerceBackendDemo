package hr.tvz.klobucaric.hardwareapp.repository;

import hr.tvz.klobucaric.hardwareapp.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Authority, Long> {

    Authority findAuthorityByName(String name);

}
