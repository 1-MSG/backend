package spharos.msg.domain.cart.service.V2;

import static spharos.msg.domain.cart.converter.CartConverter.toDto;
import static spharos.msg.domain.cart.converter.CartConverter.toEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.cart.converter.CartConverter;
import spharos.msg.domain.cart.dto.CartProductResponseDto;
import spharos.msg.domain.cart.entity.CartProduct;
import spharos.msg.domain.cart.repository.CartProductRepository;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Service
@RequiredArgsConstructor
public class CartProductUpdateServiceV2 {
    private final ProductOptionRepository productOptionRepository;
    private final CartProductRepository cartProductRepository;

    @Transactional
    public ApiResponse<CartProductResponseDto> updateCartProductOption(Long productOptionId, Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        ProductOption productOption = productOptionRepository.findById(productOptionId).orElseThrow();
        cartProductRepository.save(CartConverter.toEntity(cartId,cartProduct, productOption));
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                toDto(cartProduct));
    }
    @Transactional
    public ApiResponse<CartProductResponseDto> addCartProductQuantity(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        cartProductRepository.save(CartConverter.toEntity(cartId,cartProduct,cartProduct.getCartProductQuantity()+1));
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                toDto(cartProduct));
    }
    @Transactional
    public ApiResponse<CartProductResponseDto> minusCartProductQuantity(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        cartProductRepository.save(CartConverter.toEntity(cartId,cartProduct,cartProduct.getCartProductQuantity()-1));
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                toDto(cartProduct));
    }
    @Transactional
    public ApiResponse<CartProductResponseDto> checkCartProduct(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        cartProductRepository.save(CartConverter.toEntity(cartId,cartProduct,true));
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                toDto(cartProduct));
    }

    @Transactional
    public ApiResponse<CartProductResponseDto> notCheckCartProduct(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        cartProductRepository.save(CartConverter.toEntity(cartId,cartProduct,false));
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                toDto(cartProduct));
    }
    @Transactional
    public ApiResponse<CartProductResponseDto> pinCartProduct(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        cartProductRepository.save(toEntity(cartId,true,cartProduct));
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                toDto(cartProduct));
    }

    @Transactional
    public ApiResponse<CartProductResponseDto> notPinCartProduct(Long cartId) {
        CartProduct cartProduct = getCartProduct(cartId);
        cartProductRepository.save(toEntity(cartId,false,cartProduct));
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_UPDATE_SUCCESS,
                toDto(cartProduct));
    }

    private CartProduct getCartProduct(Long cartId) {
        return cartProductRepository.findById(cartId).orElseThrow();
    }
}
