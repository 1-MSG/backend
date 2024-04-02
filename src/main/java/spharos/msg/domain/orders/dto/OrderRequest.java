package spharos.msg.domain.orders.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {

    @Getter
    @AllArgsConstructor
    public static class OrderSheetDto {

        private Long buyerId;
        private String buyerName;
        private String buyerPhoneNumber;
        private String address;
        private List<OrderProduct> orderProducts;
    }

    @Getter
    @AllArgsConstructor
    public static class OrderProduct {

        Long productId;
        Long productOptionId;
        Integer orderQuantity;
        Integer orderDeliveryFee;
        BigDecimal discountRate;
        Long salePrice;
        Long originPrice;
    }
}
