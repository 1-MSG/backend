package spharos.msg.domain.orders.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class OrderResultDto {

        private Long totalPrice;
        private String address;
        private String phoneNumber;
        private String username;
        private LocalDateTime createdAt;
        List<OrderPrice> orderPrices;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderPrice {

        private int deliveryFee;
        private Long productOriginPrice;
        private Long productSalePrice;
    }

    @NoArgsConstructor
    @Getter
    @ToString
    public static class OrderHistoryDto {

        Long orderId;
        Long totalPrice;
        LocalDateTime createdAt;

        @QueryProjection
        public OrderHistoryDto(Long orderId, Long totalPrice, LocalDateTime createdAt) {
            this.orderId = orderId;
            this.totalPrice = totalPrice;
            this.createdAt = createdAt;
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class OrderUserDto {

        private String loginId;
        private String username;
        private String address;
        private String phoneNumber;
        private String email;

        @QueryProjection
        public OrderUserDto(String loginId, String username, String address, String phoneNumber,
            String email) {
            this.loginId = loginId;
            this.username = username;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }
    }

    @NoArgsConstructor
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

        @QueryProjection
        public OrderProductDto(Long productId, String productName, Long productPrice, String image,
            Integer productQuantity, BigDecimal discountRate, String option) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.image = image;
            this.productQuantity = productQuantity;
            this.discountRate = discountRate;
            this.option = option;
        }
    }
}
