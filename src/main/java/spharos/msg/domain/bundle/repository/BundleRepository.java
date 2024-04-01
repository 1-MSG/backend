package spharos.msg.domain.bundle.repository;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.bundle.entity.Bundle;

@Repository
public interface BundleRepository extends JpaRepository<Bundle,Long> {
    Page<Bundle> findAll(Pageable pageable);
}
