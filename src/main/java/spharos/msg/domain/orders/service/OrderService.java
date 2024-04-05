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
import spharos.msg.domain.orders.dto.OrderResponse.OrderUserDto;
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


    public OrderUserDto findOrderUser(String uuid) {
        Users user = usersRepository
            .findByUuid(uuid)
            .orElseThrow(() -> new OrderException(ErrorStatus.FIND_USER_INFO_FAIL));

        return OrderUserDto
            .builder()
            .loginId(user.getLoginId())
            .email(user.getEmail())
            .username(user.getUsername())
            .phoneNumber(user.getPhoneNumber())
            .address(user.getAddress())
            .build();
    }

    @Transactional
    public Orders saveOrder(OrderSheetDto orderSheetDto) {
        Orders newOrder = toOrderEntity(orderSheetDto);
        List<OrderProductDetail> orderProductDetails = orderSheetDto.getOrderProductDetails();
        orderProductDetails.forEach(this::decreaseStock);
        return orderRepository.save(newOrder);
    }

    public OrderResultDto createOrderResult(Long orderId, List<OrderPrice> orderPrices) {
        Orders orders = orderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderException(ErrorStatus.ORDER_ID_NOT_FOUND));

        return OrderResultDto
            .builder()
            .orderPrices(orderPrices)
            .createdAt(orders.getCreatedAt())
            .address(orders.getAddress())
            .username(orders.getUsername())
            .totalPrice(orders.getTotalPrice())
            .phoneNumber(orders.getUserPhoneNumber())
            .build();
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

        return orderRepository.findAllByUserId(users.getId());
    }

    private Orders toOrderEntity(OrderSheetDto orderSheetDto) {
        return Orders.builder()
            .userId(orderSheetDto.getBuyerId())
            .username(orderSheetDto.getBuyerName())
            .userPhoneNumber(orderSheetDto.getBuyerPhoneNumber())
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
