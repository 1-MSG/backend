package spharos.msg.domain.orders.service;

import static spharos.msg.domain.orders.dto.OrderResponse.OrderResultDto;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.orders.dto.OrderRequest.OrderProductDetail;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderHistoryDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderPrice;
import spharos.msg.domain.orders.entity.Orders;
import spharos.msg.domain.orders.repository.OrderRepository;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.OrderException;
import spharos.msg.global.api.exception.UsersException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
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
        List<OrderProductDetail> orderProductDetails = orderSheetDto.getOrderProductDetails();
        List<OrderPrice> orderPrices = createOrderPrice(orderProductDetails);

        orderProductDetails.forEach(this::decreaseStock);
        return toOrderResultDto(newOrder, orderPrices);
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
        Users users = usersRepository.findByUuid(uuid)
            .orElseThrow(() -> new UsersException(ErrorStatus.NOT_USER));

        return orderRepository.findAllByBuyerId(users.getId());
    }

    private Orders toOrderEntity(OrderSheetDto orderSheetDto) {
        return Orders.builder()
            .buyerId(orderSheetDto.getBuyerId())
            .buyerName(orderSheetDto.getBuyerName())
            .buyerPhoneNumber(orderSheetDto.getBuyerPhoneNumber())
            .address(orderSheetDto.getAddress())
            .totalPrice(getTotalPrice(orderSheetDto.getOrderProductDetails()))
            .build();
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

    private OrderResultDto toOrderResultDto(Orders newOrder, List<OrderPrice> orderPrices) {
        return OrderResultDto
            .builder()
            .orderId(newOrder.getId())
            .address(newOrder.getAddress())
            .phoneNumber(newOrder.getUserPhoneNumber())
            .totalPrice(newOrder.getTotalPrice())
            .orderPrices(orderPrices)
            .build();
    }

    private List<OrderPrice> createOrderPrice(List<OrderProductDetail> orderProductDetails) {
        return orderProductDetails
            .stream()
            .map(orderProductDetail -> new OrderPrice(
                orderProductDetail.getOrderDeliveryFee(),
                orderProductDetail.getOriginPrice(),
                orderProductDetail.getSalePrice()))
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
