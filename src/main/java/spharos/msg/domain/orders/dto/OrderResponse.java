package spharos.msg.domain.orders.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class OrderResultDto {

        private Long totalPrice;
        private String address;
        private String phoneNumber;
        private Long orderId;
        List<OrderPrice> orderPrices;

    }

    @Getter
    @AllArgsConstructor
    public static class OrderPrice {

        private int deliveryFee;
        private Long productOriginPrice;
        private Long productSalePrice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class OrderHistoryDto {

        Long orderId;
        Long totalPrice;
        LocalDateTime createdAt;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class OrderUserDto {

        private String loginId;
        private String username;
        private String address;
        private String phoneNumber;
        private String email;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class OrderProductDto {

        Long productId;
        String productName;
        Long productPrice;
        String image;
        Integer productQuantity;
        BigDecimal discountRate;
        String option;
    }
}
