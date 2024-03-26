package spharos.msg.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.cart.entity.CartProduct;

@Getter
@NoArgsConstructor
public class CartProductResponseDto {
    private Long productId;
    private String productName;
    private Integer productPrice;
    private boolean cartIsChecked;
    private Long productOptionId;
    //todo 이미지, 별점 정보 가져오기
    //private String image;
    //private Integer productStar;
    private Integer productQuantity;
    private Integer productStock;

    public CartProductResponseDto(CartProduct cartProduct) {
        this.productId = cartProduct.getProductOption().getProduct().getId();
        this.productName = cartProduct.getProductOption().getProduct().getProductName();
        this.productPrice = cartProduct.getProductOption().getProduct().getProductPrice();
        this.cartIsChecked = cartProduct.getCartIsChecked();
        this.productOptionId = cartProduct.getProductOption().getProductOptionId();
//        this.image = cartProduct.getProductOption().getProduct().get
//        this.productStar =
        this.productQuantity = cartProduct.getCartProductQuantity();
        this.productStock = cartProduct.getProductOption().getStock();
    }
}
