package spharos.msg.domain.orders.service;

import static spharos.msg.domain.orders.dto.OrderResponse.OrderResultDto;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.orders.dto.OrderRequest.OrderProduct;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderProductDetail;
import spharos.msg.domain.orders.entity.Orders;
import spharos.msg.domain.orders.repository.OrderRepository;
import spharos.msg.domain.product.repository.ProductOptionRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductOptionRepository productOptionRepository;


    /*
     TODO : 현재 수정해야 하는 로직
    public OrderUserDto findOrderUser(String uuid) {
        return OrderUserDto
            .builder()
            .loginId(user.getLoginId())
            .email(user.getEmail())
            .username(user.getUsername())
            .phoneNumber(user.getPhoneNumber())
            .address(address)
            .build();
    }
    */

    @Transactional
    public OrderResultDto saveOrder(OrderSheetDto orderSheetDto) {
        Orders newOrder = orderRepository.save(toOrderEntity(orderSheetDto));
        List<OrderProductDetail> orderProductDetails = getOrderProductDetails(
            orderSheetDto.getOrderProducts());
        return toOrderResultDto(newOrder, orderProductDetails);
    }

    private Orders toOrderEntity(OrderSheetDto orderSheetDto) {
        return Orders.builder()
            .buyerId(orderSheetDto.getBuyerId())
            .buyerName(orderSheetDto.getBuyerName())
            .buyerPhoneNumber(orderSheetDto.getBuyerPhoneNumber())
            .address(orderSheetDto.getAddress())
            .totalPrice(getTotalPrice(orderSheetDto.getOrderProducts()))
            .build();
    }


    private Long getTotalPrice(List<OrderProduct> orderProducts) {
        Long totalPrice = 0L;
        for (OrderProduct orderProduct : orderProducts) {
            Long productPrice = orderProduct.getOrderQuantity() * orderProduct.getSalePrice();
            totalPrice += productPrice + orderProduct.getOrderDeliveryFee();
        }
        return totalPrice;
    }

    private OrderResultDto toOrderResultDto(Orders newOrder,
        List<OrderProductDetail> orderProductDetails) {
        return OrderResultDto
            .builder()
            .orderId(newOrder.getId())
            .address(newOrder.getAddress())
            .phoneNumber(newOrder.getBuyerPhoneNumber())
            .totalPrice(newOrder.getTotalPrice())
            .orderProductDetails(orderProductDetails)
            .build();
    }

    private List<OrderProductDetail> getOrderProductDetails(List<OrderProduct> orderProducts) {
        return orderProducts
            .stream()
            .map(orderProduct -> new OrderProductDetail(
                orderProduct.getOrderDeliveryFee(),
                orderProduct.getOriginPrice(),
                orderProduct.getSalePrice()))
            .toList();
    }

    /*
    현재 사용되지 않음
    private Product findProductByOption(Long optionId) {
        return productOptionRepository
            .findById(optionId)
            .orElseThrow(() -> new OrderException(ErrorStatus.ORDER_PRODUCT_NOT_FOUND))
            .getProduct();
    }
    */
}
