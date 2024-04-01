package spharos.msg.domain.bundle.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.bundle.entity.Bundle;
import spharos.msg.domain.bundle.entity.BundleProduct;

@Repository
public interface BundleProductRepository extends JpaRepository<BundleProduct, Long> {
    List<BundleProduct> findAll(Bundle bundle);
}
