package spharos.msg.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.cart.entity.CartProduct;

@Getter
@NoArgsConstructor
public class CartProductResponseDto {
    private int id;
    private Long cartId;
    private Long brandId;
    private Long productId;
    private Integer cartProductQuantity;
    private Long productOptionId;
    private boolean cartIsChecked;
    private boolean cartIsPinned;
    private Long userId;

    public CartProductResponseDto(CartProduct cartProduct) {
        this.cartId = cartProduct.getId();
        this.productId = cartProduct.getProductId();
        this.cartProductQuantity = cartProduct.getCartProductQuantity();
        this.cartIsChecked = cartProduct.getCartIsChecked();
        this.cartIsPinned = cartProduct.getCartIsPinned();
        this.productOptionId = cartProduct.getProductOption().getProductOptionId();
        this.brandId = cartProduct.getBrandId();
        this.userId = cartProduct.getUsers().getId();
    }

    public void setId(int id) {
        this.id = id;
    }
}
