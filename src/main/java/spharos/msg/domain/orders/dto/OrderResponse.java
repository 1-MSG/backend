package spharos.msg.domain.orders.dto;

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
        List<OrderProductDetail> orderProductDetails;
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

    @Getter
    @AllArgsConstructor
    public static class OrderProductDetail {

        private int deliveryFee;
        private Long productOriginPrice;
        private Long productSalePrice;
    }
}
