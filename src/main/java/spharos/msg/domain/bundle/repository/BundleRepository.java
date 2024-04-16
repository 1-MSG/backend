package spharos.msg.domain.bundle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.bundle.entity.Bundle;

@Repository
public interface BundleRepository extends JpaRepository<Bundle,Long> {
}
