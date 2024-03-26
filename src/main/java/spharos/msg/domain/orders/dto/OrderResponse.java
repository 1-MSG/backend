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
    public static class OrderProductDto {

        List<PriceInfo> priceInfos;
        private String loginId;
        private String username;
        private String address;
        private Long orderId;
        private String phoneNumber;
        private Long totalPrice;
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
    public static class PriceInfo {

        private int deliveryFee;
        private int productPrice;
        private Long salePrice;
    }
}
