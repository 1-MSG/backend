package spharos.msg.domain.orders.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {

    @Getter
    public static class OrderDto {

        private Long productId;
        private Long productOptionId;
        private Integer quantity;
    }
}
