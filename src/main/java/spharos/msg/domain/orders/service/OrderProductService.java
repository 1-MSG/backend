package spharos.msg.domain.orders.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.orders.dto.OrderRequest.OrderProductDetail;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.orders.entity.Orders;
import spharos.msg.domain.orders.repository.OrderProductRepository;
import spharos.msg.domain.orders.repository.OrderRepository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.ProductNotExistException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderProductService {

    private static final boolean COMPLETED_DEFAULT = false;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void saveAllByOrderSheet(OrderSheetDto orderSheetDto, Long orderId) {
        List<OrderProductDetail> orderProductDetails = orderSheetDto.getOrderProductDetails();
        Orders orders = orderRepository.findById(orderId).get();
        List<OrderProduct> orderProductEntities = orderProductDetails
            .stream()
            .map(detail -> toOrderProductEntity(detail, orders))
            .toList();

        orderProductRepository.saveAll(orderProductEntities);
    }

    private OrderProduct toOrderProductEntity(OrderProductDetail orderProductDetail,
        Orders orders) {

        Product product = productRepository
            .findById(orderProductDetail.getProductId())
            .orElseThrow(() -> new ProductNotExistException(ErrorStatus.PRODUCT_ERROR));

        return OrderProduct
            .builder()
            .orderQuantity(orderProductDetail.getOrderQuantity())
            .ordersDeliveryFee(orderProductDetail.getOrderDeliveryFee())
            .discountRate(orderProductDetail.getDiscountRate())
            .productPrice(orderProductDetail.getOriginPrice())
            .productId(orderProductDetail.getProductId())
            .orderIsCompleted(COMPLETED_DEFAULT)
            .orders(orders)
            .productOption("옵션1 옵션2 옵션3") // 임시값 - 변경 예정
            .productName(product.getProductName())
            .productImage("TEMP VALUE") //임시값 - 변경 예정
            .build();
    }
}
