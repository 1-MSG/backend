package spharos.msg.domain.orders.converter;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.orders.dto.OrderRequest.OrderSheetDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderPrice;
import spharos.msg.domain.orders.dto.OrderResponse.OrderResultDto;
import spharos.msg.domain.orders.dto.OrderResponse.OrderUserDto;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.orders.entity.Orders;
import spharos.msg.domain.users.entity.Users;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdersConverter {

    public static Orders toEntity(OrderSheetDto orderSheetDto, Long totalPrice) {
        return Orders.builder()
            .userId(orderSheetDto.getBuyerId())
            .username(orderSheetDto.getBuyerName())
            .userPhoneNumber(orderSheetDto.getBuyerPhoneNumber())
            .address(orderSheetDto.getAddress())
            .totalPrice(totalPrice)
            .build();
    }

    public static OrderUserDto toDto(Users user) {
        return new OrderUserDto(
            user.getLoginId(),
            user.getUsername(),
            user.getAddress(),
            user.getPhoneNumber(),
            user.getEmail()
        );
    }

    public static OrderResultDto toDto(Orders orders, List<OrderPrice> orderPrices) {
        return new OrderResultDto(
            orders.getTotalPrice(),
            orders.getAddress(),
            orders.getUserPhoneNumber(),
            orders.getUsername(),
            orders.getCreatedAt(),
            orderPrices
        );
    }

    public static OrderPrice toDto(OrderProduct orderProduct) {
        Integer deliveryFee = orderProduct.getOrdersDeliveryFee();
        int discountRate = orderProduct.getDiscountRate().intValue();
        Long originPrice = orderProduct.getProductPrice();
        Long salePrice = originPrice * (100 - discountRate) / 100;

        return new OrderPrice(deliveryFee, originPrice, salePrice);
    }
}
