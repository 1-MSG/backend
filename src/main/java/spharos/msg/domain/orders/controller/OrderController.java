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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderHistoryDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderPrice;
import spharos.msg.domain.orders.dto.OrderResponse.OrderProductDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderUserDto;
import spharos.msg.domain.orders.entity.Orders;
import spharos.msg.domain.orders.repository.OrderProductRepository;
import spharos.msg.domain.orders.service.OrderProductService;
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
    private final OrderProductService orderProductService;
    private final OrderProductRepository orderProductRepository;

    @Operation(summary = "상품 주문",
        description = "상품을 구매하여, Order 객체를 만듭니다. 이후 order_id를 반환합니다.")
    @PostMapping("/orders")
    public ApiResponse<Long> addOrderAPI(
        @RequestBody OrderSheetDto orderSheetDto,
        @AuthenticationPrincipal UserDetails userDetails) {
        Orders newOrder = orderService.saveOrder(orderSheetDto);
        orderProductService.saveAllByOrderSheet(orderSheetDto, newOrder);

        return ApiResponse.of(
            SuccessStatus.ORDER_SUCCESS,
            newOrder.getId());
    }

    @Operation(summary = "구매 결과 조회",
        description = "order_id를 통해 구매 결과를 반환받습니다. 상품 주문 후에 바로 호출됩니다")
    @GetMapping("/order-result")
    public ApiResponse<OrderResultDto> orderResultAPI(
        @RequestParam("orderId") Long orderId
    ) {
        List<OrderPrice> orderPrices = orderProductService.createOrderPricesByOrderId(orderId);
        return ApiResponse.of(
            SuccessStatus.ORDER_SUCCESS,
            orderService.createOrderResult(orderId, orderPrices));
    }

    @Operation(summary = "회원별 주문 내역 조회를 위한 order_id 리스트 조회",
        description = "토큰을 통해 해당 회원이 주문했던 내역을 모두 order_id 배열 형태로 가져옵니다.")
    @GetMapping("/orders")
    public ApiResponse<List<OrderHistoryDto>> orderIdsAPI(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        String uuid = userDetails.getUsername();
        return ApiResponse.of(
            SuccessStatus.ORDER_HISTORY_SUCCESS,
            orderService.findOrderHistories(uuid)
        );
    }

    @Operation(summary = "orderId를 통한 주문 내역 상세 조회",
        description = "id를 통해 관련된 주문 내역을 가져옵니다.")
    @GetMapping("/order-product")
    public ApiResponse<List<OrderProductDto>> orderProductHistoryAPI(
        @RequestParam("orderId") Long orderId
    ) {
        return ApiResponse.of(
            SuccessStatus.ORDER_HISTORY_SUCCESS,
            orderProductRepository.findAllById(orderId));
    }

    @Operation(summary = "주문자 정보 조회", description = "토큰을 통해 주문자의 정보를 조회합니다.")
    @GetMapping("/user")
    public ApiResponse<OrderUserDto> orderUserAPI(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        String uuid = userDetails.getUsername();
        OrderUserDto orderUserDto = orderService.findOrderUser(uuid);
        return ApiResponse.of(SuccessStatus.ORDER_USER_SUCCESS, orderUserDto);
    }
}
