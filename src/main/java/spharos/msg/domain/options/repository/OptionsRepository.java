package spharos.msg.domain.options.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.options.entity.Options;

public interface OptionsRepository extends JpaRepository<Options, Long> {
}
