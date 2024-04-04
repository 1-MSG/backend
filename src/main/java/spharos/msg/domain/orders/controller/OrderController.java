package spharos.msg.domain.orders.controller;

import static spharos.msg.domain.orders.dto.OrderResponse.OrderResultDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderHistoryDto;
import spharos.msg.domain.orders.service.OrderService;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Order", description = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "상품 주문", description = "상품을 구매하여, Order 객체를 만든다.")
    @PostMapping("/orders")
    public ApiResponse<OrderResultDto> addOrderAPI(
        @RequestBody OrderSheetDto orderSheetDto,
        @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.of(
            SuccessStatus.ORDER_SUCCESS,
            orderService.saveOrder(orderSheetDto));
    }

    @Operation(summary = "회원별 주문 내역 조회를 위한 order_id 리스트 조회",
        description = "토큰을 통해 해당 회원이 주문했던 내역을 모두 order_id 배열 형태로 가져옵니다.")
    @GetMapping("/orders")
    public ApiResponse<List<OrderHistoryDto>> getOrderIdsAPI(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        String uuid = userDetails.getUsername();
        return ApiResponse.of(
            SuccessStatus.ORDER_HISTORY_SUCCESS,
            orderService.findOrderHistories(uuid)
        );
    }

    /*
    TODO : API 수정으로 인한 보완 필요
    @Operation(summary = "주문자 정보 조회", description = "토큰을 통해 주문자의 정보를 조회합니다.")
    @GetMapping("/user")
    public ApiResponse<?> orderUserAPI(
        @Parameter(hidden = true)
        @RequestHeader(AUTHORIZATION) String token) {
        String uuid = jwtTokenProvider.validateAndGetUserUuid(token);
        OrderUserDto orderUserDto = orderService.findOrderUser(uuid);
        return ApiResponse.of(SuccessStatus.ORDER_USER_SUCCESS, orderUserDto);
    }
    */
}
