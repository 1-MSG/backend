package spharos.msg.domain.admin.service;

import static spharos.msg.domain.admin.dto.AdminResponseDto.CountPriceDto;

import java.util.Collection;
import java.util.List;
import java.util.function.ToLongFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.admin.entity.CountPrice;
import spharos.msg.domain.admin.repository.CountPriceRepository;
import spharos.msg.domain.orders.dto.OrderResponse.OrderPrice;
import spharos.msg.domain.orders.repository.OrderRepository;
import spharos.msg.domain.orders.service.OrderProductService;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CountPriceService {

    private static final long FIXED_TABLE_ID = 1L;
    private final OrderProductService orderProductService;
    private final CountPriceRepository countPriceRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CountPrice updateCountPrice() {
        List<Long> orderIds = orderRepository.findAllId();
        List<OrderPrice> orderPrices = findCountPriceElements(orderIds);

        Long totalSalesPrice = getSum(orderPrices, OrderPrice::getProductSalePrice);
        Long totalDeliveryFee = getSum(orderPrices, OrderPrice::getDeliveryFee);
        Long totalProfit = totalSalesPrice - totalDeliveryFee;

        return countPriceRepository.save(
            new CountPrice(FIXED_TABLE_ID, totalSalesPrice, totalDeliveryFee, totalProfit));
    }

    private List<OrderPrice> findCountPriceElements(List<Long> orderIds) {
        return orderIds
            .stream()
            .map(orderProductService::createOrderPricesByOrderIdV1)
            .flatMap(Collection::stream)
            .toList();
    }

    private long getSum(List<OrderPrice> orderPrices, ToLongFunction<OrderPrice> convertValue) {
        return orderPrices
            .stream()
            .mapToLong(convertValue)
            .sum();
    }

    public CountPriceDto findCountPrice() {
        CountPrice countPrice = countPriceRepository
            .findFirstById(FIXED_TABLE_ID)
            .get();

        return toDto(countPrice);
    }

    private CountPriceDto toDto(CountPrice countPrice) {
        return CountPriceDto
            .builder()
            .totalProfit(countPrice.getTotalProfit())
            .totalDeliveryPrice(countPrice.getTotalDeliveryFee())
            .totalOrderPrice(countPrice.getTotalSalesPrice())
            .build();
    }
}
