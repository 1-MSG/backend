package spharos.msg.domain.admin.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.admin.dto.AdminPriceResponseDto;
import spharos.msg.domain.admin.entity.CountPrice;

@Repository
public interface CountPriceRepository extends JpaRepository<CountPrice, Long> {
    Optional<AdminPriceResponseDto.TotalSalesPrice> findTotalSalesPriceById(Long id);
}
