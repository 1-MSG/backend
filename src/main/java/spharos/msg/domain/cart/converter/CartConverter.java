package spharos.msg.domain.cart.converter;

import spharos.msg.domain.cart.dto.CartProductRequestDto;
import spharos.msg.domain.cart.dto.CartProductResponseDto;
import spharos.msg.domain.cart.entity.CartProduct;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.users.entity.Users;

public class CartConverter {
    static public CartProductResponseDto CartEntityToDto(CartProduct cartProduct){

        return new CartProductResponseDto(cartProduct);
    }

    static public CartProduct CartDtoToEntity(Users users,
                                          CartProductRequestDto cartProductRequestDto,
                                          ProductOption productOption,
                                          Integer productQuantity){
        return CartProduct.builder()
                .productId(cartProductRequestDto.getProductId())
                .brandId(cartProductRequestDto.getBrandId())
                .cartProductQuantity(productQuantity)
                .productOption(productOption)
                .cartIsChecked(false)
                .cartIsPinned(false)
                .users(users)
                .build();
    }
    static public CartProduct CartDtoToEntity(CartProduct cartProduct,
                                              Integer productQuantity){
        return CartProduct.builder()
                .id(cartProduct.getId())
                .cartProductQuantity(cartProduct.getCartProductQuantity() + productQuantity)
                .productOption(cartProduct.getProductOption())
                .cartIsChecked(cartProduct.getCartIsChecked())
                .cartIsPinned(cartProduct.getCartIsPinned())
                .productId(cartProduct.getProductId())
                .brandId(cartProduct.getBrandId())
                .users(cartProduct.getUsers())
                .build();
    }
}