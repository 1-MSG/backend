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
    public ApiResponse<?> updateCartProductOption(Long productOptionId, Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        ProductOption productOption = productOptionRepository.findById(productOptionId).orElseThrow();
            cartProduct.updateCartProductOption(productOption);
            return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                    new CartProductResponseDto(cartProduct));
    }

    @Transactional
    public ApiResponse<?> addCartProductQuantity(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
            cartProduct.addOneCartProductQuantity();
            return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                    new CartProductResponseDto(cartProduct));
    }

    @Transactional
    public ApiResponse<?> minusCartProductQuantity(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
            cartProduct.minusOneCartProductQuantity();
            return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                    new CartProductResponseDto(cartProduct));
    }

    @Transactional
    public ApiResponse<?> checkCartProduct(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
            cartProduct.checkCartProduct();
            return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                    new CartProductResponseDto(cartProduct));

    }

    @Transactional
    public ApiResponse<?> notCheckCartProduct(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
            cartProduct.notCheckCartProduct();
            return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                    new CartProductResponseDto(cartProduct));
    }

    private CartProduct getCartProduct(Long cartId) {
        return cartProductRepository.findById(cartId).orElseThrow();
    }
}
