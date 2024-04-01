package spharos.msg.domain.option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.option.entity.Options;

public interface OptionRepository extends JpaRepository<Options, Long> {
}
