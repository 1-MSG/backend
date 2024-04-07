package spharos.msg.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.orders.repository.OrderRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin Orders", description = "어드민 주문 관련 페이지")
public class CountPriceController {

    private final OrderRepository orderRepository;

    @Operation(summary = "누적 주문 횟수 조회",
        description = "Order 테이블의 개수를 조회함으로써 총 주문이 몇번 발생했는지를 조회합니다.")
    @GetMapping("/orders-count")
    public ApiResponse<Long> countOrdersAPI() {
        return ApiResponse.of(
            SuccessStatus.ORDER_COUNT_SUCCESS,
            orderRepository.count());
    }
}
