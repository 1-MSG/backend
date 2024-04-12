package spharos.msg.domain.orders.service;

import static spharos.msg.domain.orders.dto.OrderResponse.OrderResultDto;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.orders.converter.OrdersConverter;
import spharos.msg.domain.orders.dto.OrderRequest.OrderProductDetail;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderHistoryDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderPrice;
import spharos.msg.domain.orders.dto.OrderResponse.OrderUserDto;
import spharos.msg.domain.orders.entity.Orders;
import spharos.msg.domain.orders.repository.OrderRepository;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.OrderException;
import spharos.msg.global.api.exception.UsersException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceV2 {

    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
    private final ProductOptionRepository productOptionRepository;

    public OrderUserDto findOrderUser(String uuid) {
        return usersRepository
            .findOrderUserDtoByUuid(uuid)
            .orElseThrow(() -> new OrderException(ErrorStatus.FIND_USER_INFO_FAIL));
    }

    @Transactional
    public Orders saveOrder(OrderSheetDto orderSheetDto) {
        Long totalPrice = getTotalPrice(orderSheetDto.getOrderProductDetails());
        Orders newOrder = OrdersConverter.toEntity(orderSheetDto, totalPrice);
        List<OrderProductDetail> orderProductDetails = orderSheetDto.getOrderProductDetails();
        orderProductDetails.forEach(this::decreaseStock);
        return orderRepository.save(newOrder);
    }

    public OrderResultDto createOrderResult(Long orderId, List<OrderPrice> orderPrices) {
        Orders orders = orderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderException(ErrorStatus.ORDER_ID_NOT_FOUND));

        return OrdersConverter.toDto(orders, orderPrices);
    }

    private void decreaseStock(OrderProductDetail product) {
        ProductOption optionProduct = productOptionRepository
            .findById(product.getProductOptionId())
            .orElseThrow(() -> new OrderException(ErrorStatus.NOT_EXIST_PRODUCT_OPTION));

        ProductOption updatedProductOption = ProductOption
            .builder()
            .stock(optionProduct.getStock() - product.getOrderQuantity())
            .id(optionProduct.getId())
            .product(optionProduct.getProduct())
            .options(optionProduct.getOptions())
            .build();

        productOptionRepository.save(updatedProductOption);
    }

    public List<OrderHistoryDto> findOrderHistories(String uuid) {
        Long userId = usersRepository.findIdByUuid(uuid)
            .orElseThrow(() -> new UsersException(ErrorStatus.NOT_USER));

        return orderRepository.findAllByUserId(userId);
    }

    private Long getTotalPrice(List<OrderProductDetail> orderProductDetails) {
        Long totalPrice = 0L;
        for (OrderProductDetail orderProductDetail : orderProductDetails) {
            Long productPrice =
                orderProductDetail.getOrderQuantity() * orderProductDetail.getSalePrice();
            totalPrice += productPrice + orderProductDetail.getOrderDeliveryFee();
        }
        return totalPrice;
    }
}
