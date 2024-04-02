package spharos.msg.domain.cart.dto;

import lombok.Getter;

@Getter
public class CartProductRequestDto {
    private Long brandId;
    private Long productId;
}