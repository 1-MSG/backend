package spharos.msg.domain.option.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.option.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
