package spharos.msg.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.admin.dto.AdminPriceResponseDto;
import spharos.msg.domain.admin.repository.CountPriceRepository;

@Service
@RequiredArgsConstructor
public class CountPriceService {

    private final CountPriceRepository countPriceRepository;

    public AdminPriceResponseDto.TotalSalesPrice TotalSalesPrice(){
        return countPriceRepository.findTotalSalesPriceById(1L)
                .orElseThrow();
    }
}
