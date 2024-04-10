package spharos.msg.domain.orders.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.orders.dto.OrderRequest.OrderProductDetail;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.orders.entity.Orders;
import spharos.msg.domain.product.entity.Product;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProductConverter {

    private static final boolean COMPLETED_DEFAULT = false;

    public static OrderProduct toEntity(
        OrderProductDetail orderProductDetail, Product product, Orders orders,
        String productOptions) {
        return OrderProduct
            .builder()
            .orderQuantity(orderProductDetail.getOrderQuantity())
            .ordersDeliveryFee(orderProductDetail.getOrderDeliveryFee())
            .discountRate(orderProductDetail.getDiscountRate())
            .productPrice(orderProductDetail.getOriginPrice())
            .productId(orderProductDetail.getProductId())
            .orderIsCompleted(COMPLETED_DEFAULT)
            .orders(orders)
            .productOption(productOptions)
            .productName(product.getProductName())
            .productImage("TEMP VALUE") //임시값 - 변경 예정
            .build();
    }
}
