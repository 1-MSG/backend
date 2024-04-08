package spharos.msg.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.admin.dto.AdminPriceResponseDto;
import spharos.msg.domain.admin.service.CountPriceService;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/price")
@Tag(name = "Admin price", description = "어드민 금액 집계 관련 페이지")
public class CountPriceController {

    private final CountPriceService countPriceService;
    @Operation(summary = "총 주문 금액 반환 API", description = "총 주문 금액을 반환 합니다.")
    @GetMapping("/total-price")
    private ApiResponse<AdminPriceResponseDto.TotalSalesPrice> TotalSalesPriceApi() {
        return ApiResponse.of(SuccessStatus.COUNT_CONNECT_USERS_SUCCESS, countPriceService.TotalSalesPrice());
    }
}
