package spharos.msg.domain.brand.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.msg.domain.brand.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand,Long> {
}
