package spharos.msg.domain.cart.converter;

import spharos.msg.domain.cart.dto.CartProductRequestDto;
import spharos.msg.domain.cart.dto.CartProductResponseDto;
import spharos.msg.domain.cart.entity.CartProduct;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.users.entity.Users;

public class CartConverter {
    static public CartProductResponseDto toDto(CartProduct cartProduct) {
        return CartProductResponseDto.builder()
                .cartProductQuantity(cartProduct.getCartProductQuantity())
                .productOptionId(cartProduct.getProductOption().getId())
                .cartIsChecked(cartProduct.getCartIsChecked())
                .cartIsPinned(cartProduct.getCartIsPinned())
                .productId(cartProduct.getProductId())
                .brandId(cartProduct.getBrandId())
                .cartId(cartProduct.getId())
                .build();
    }

    static public CartProduct toEntity(
            Users users,
            CartProductRequestDto cartProductRequestDto,
            ProductOption productOption,
            Integer productQuantity) {
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
    //장바구니 담을 때
    static public CartProduct toEntity(
            CartProduct cartProduct,
            Integer productQuantity) {
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

    //옵션 수정
    static public CartProduct toEntity(Long cartId, CartProduct cartProduct, ProductOption productOption) {
        return CartProduct.builder()
                .cartProductQuantity(cartProduct.getCartProductQuantity())
                .cartIsChecked(cartProduct.getCartIsChecked())
                .cartIsPinned(cartProduct.getCartIsPinned())
                .productId(cartProduct.getProductId())
                .brandId(cartProduct.getBrandId())
                .users(cartProduct.getUsers())
                .productOption(productOption)
                .id(cartId)
                .build();
    }

    //개수 수정
    static public CartProduct toEntity(Long cartId, CartProduct cartProduct, int cartProductQuantity) {
        return CartProduct.builder()
                .cartProductQuantity(cartProductQuantity)
                .cartIsChecked(cartProduct.getCartIsChecked())
                .cartIsPinned(cartProduct.getCartIsPinned())
                .productId(cartProduct.getProductId())
                .brandId(cartProduct.getBrandId())
                .users(cartProduct.getUsers())
                .productOption(cartProduct.getProductOption())
                .id(cartId)
                .build();
    }

    //체크 수정
    static public CartProduct toEntity(Long cartId, CartProduct cartProduct, boolean isChecked) {
        return CartProduct.builder()
                .cartProductQuantity(cartProduct.getCartProductQuantity())
                .cartIsChecked(isChecked)
                .cartIsPinned(cartProduct.getCartIsPinned())
                .productId(cartProduct.getProductId())
                .brandId(cartProduct.getBrandId())
                .users(cartProduct.getUsers())
                .productOption(cartProduct.getProductOption())
                .id(cartId)
                .build();
    }

    //핀 수정
    static public CartProduct toEntity(Long cartId, boolean isPinned, CartProduct cartProduct) {
        return CartProduct.builder()
                .cartProductQuantity(cartProduct.getCartProductQuantity())
                .cartIsChecked(cartProduct.getCartIsChecked())
                .cartIsPinned(isPinned)
                .productId(cartProduct.getProductId())
                .brandId(cartProduct.getBrandId())
                .users(cartProduct.getUsers())
                .productOption(cartProduct.getProductOption())
                .id(cartId)
                .build();
    }


}