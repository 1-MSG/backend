package spharos.msg.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.cart.entity.CartProduct;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductResponseDto {

    private int id;
    private Long cartId;
    private Long brandId;
    private Long productId;
    private Integer cartProductQuantity;
    private Long productOptionId;
    private boolean cartIsChecked;
    private boolean cartIsPinned;

    public CartProductResponseDto(CartProduct cartProduct) {
        this.cartId = cartProduct.getId();
        this.productId = cartProduct.getProductId();
        this.cartProductQuantity = cartProduct.getCartProductQuantity();
        this.cartIsChecked = cartProduct.getCartIsChecked();
        this.cartIsPinned = cartProduct.getCartIsPinned();
        this.productOptionId = cartProduct.getProductOption().getId();
        this.brandId = cartProduct.getBrandId();
    }

    public void setId(int id) {
        this.id = id;
    }
}
