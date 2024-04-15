package spharos.msg.domain.admin.controller.v2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.admin.dto.AdminResponseDto.CountPriceDto;
import spharos.msg.domain.admin.service.CountPriceServiceV2;
import spharos.msg.domain.orders.repository.OrderRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/admin")
@Slf4j
@Tag(name = "Admin Orders", description = "어드민 주문 관련 페이지")
public class CountPriceControllerV2 {

    private final CountPriceServiceV2 countPriceService;
    private final OrderRepository orderRepository;

    @Operation(summary = "누적 주문 횟수 조회",
            description = "Order 테이블의 개수를 조회함으로써 총 주문이 몇번 발생했는지를 조회합니다.")
    @GetMapping("/orders-count")
    public ApiResponse<Long> countOrdersAPI() {
        return ApiResponse.of(
                SuccessStatus.ORDER_COUNT_SUCCESS,
                orderRepository.count());
    }

    @Operation(summary = "주문 매출 통계 조회",
            description = "COUNT_PRICE 테이블을 전체를 조회합니다.")
    @GetMapping("/orders-price")
    public ApiResponse<CountPriceDto> countPriceAPI() {
        return ApiResponse.of(
                SuccessStatus.ORDER_COUNT_SUCCESS,
                countPriceService.findCountPrice());
    }

    /*
    3시간에 한번씩 해당 함수 실행
     */
    @Scheduled(cron = "0 0 */3 * * *")
    public void updateCountPrice() {
        log.info("update 함수 실행");
        countPriceService.updateCountPrice();
    }
}
