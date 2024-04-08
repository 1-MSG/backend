package spharos.msg.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.cart.dto.CartProductResponseDto;
import spharos.msg.domain.cart.entity.CartProduct;
import spharos.msg.domain.cart.repository.CartProductRepository;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Service
@RequiredArgsConstructor
public class CartProductUpdateService {
    private final ProductOptionRepository productOptionRepository;
    private final CartProductRepository cartProductRepository;

    @Transactional
    public ApiResponse<CartProductResponseDto> updateCartProductOption(Long productOptionId, Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        ProductOption productOption = productOptionRepository.findById(productOptionId).orElseThrow();

        cartProductRepository.save(CartProduct.builder()
                .id(cartId)
                .cartProductQuantity(cartProduct.getCartProductQuantity())
                .productOption(productOption)
                .cartIsChecked(cartProduct.getCartIsChecked())
                .users(cartProduct.getUsers())
                .build());

        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                new CartProductResponseDto(cartProduct));
    }

    @Transactional
    public ApiResponse<CartProductResponseDto> addCartProductQuantity(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);

        cartProductRepository.save(CartProduct.builder()
                .id(cartId)
                .cartProductQuantity(cartProduct.getCartProductQuantity()+1)
                .productOption(cartProduct.getProductOption())
                .cartIsChecked(cartProduct.getCartIsChecked())
                .users(cartProduct.getUsers())
                .build());

        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                new CartProductResponseDto(cartProduct));
    }

    @Transactional
    public ApiResponse<CartProductResponseDto> minusCartProductQuantity(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);

        cartProductRepository.save(CartProduct.builder()
                .id(cartId)
                .cartProductQuantity(cartProduct.getCartProductQuantity()-1)
                .productOption(cartProduct.getProductOption())
                .cartIsChecked(cartProduct.getCartIsChecked())
                .users(cartProduct.getUsers())
                .build());

        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                new CartProductResponseDto(cartProduct));
    }

    @Transactional
    public ApiResponse<CartProductResponseDto> checkCartProduct(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);

        cartProductRepository.save(CartProduct.builder()
                .id(cartId)
                .cartProductQuantity(cartProduct.getCartProductQuantity())
                .productOption(cartProduct.getProductOption())
                .cartIsChecked(true)
                .users(cartProduct.getUsers())
                .build());

        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                new CartProductResponseDto(cartProduct));

    }

    @Transactional
    public ApiResponse<CartProductResponseDto> notCheckCartProduct(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);

        cartProductRepository.save(CartProduct.builder()
                .id(cartId)
                .cartProductQuantity(cartProduct.getCartProductQuantity())
                .productOption(cartProduct.getProductOption())
                .cartIsChecked(false)
                .users(cartProduct.getUsers())
                .build());

        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                new CartProductResponseDto(cartProduct));
    }

    private CartProduct getCartProduct(Long cartId) {
        return cartProductRepository.findById(cartId).orElseThrow();
    }
}
