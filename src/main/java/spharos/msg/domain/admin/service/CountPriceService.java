package spharos.msg.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.admin.dto.AdminPriceResponseDto;
import spharos.msg.domain.admin.repository.CountPriceRepository;

@Service
@RequiredArgsConstructor
public class CountPriceService {

    private final CountPriceRepository countPriceRepository;

    public AdminPriceResponseDto.TotalSalesPrice TotalSalesPrice() {
        return countPriceRepository.findDataById(1L, AdminPriceResponseDto.TotalSalesPrice.class)
                .orElseThrow();
    }

    public AdminPriceResponseDto.TotalDeliveryFee TotalDeliveryFee() {
        return countPriceRepository.findDataById(1L, AdminPriceResponseDto.TotalDeliveryFee.class)
                .orElseThrow();
    }

    public AdminPriceResponseDto.TotalProfit TotalProfit() {
        return countPriceRepository.findDataById(1L, AdminPriceResponseDto.TotalProfit.class)
                .orElseThrow();
    }
}
