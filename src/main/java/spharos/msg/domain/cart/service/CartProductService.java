package spharos.msg.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.cart.dto.CartProductOptionResponseDto;
import spharos.msg.domain.cart.dto.CartProductRequestDto;
import spharos.msg.domain.cart.dto.CartProductResponseDto;
import spharos.msg.domain.cart.entity.CartProduct;
import spharos.msg.domain.cart.repository.CartProductRepository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CartProductService {
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public ApiResponse<?> addCartProduct(Long productOptionId, CartProductRequestDto cartProductRequestDto, int cartProductQuantity, String userUuid) {
        ProductOption productOption = productOptionRepository.findById(productOptionId).orElseThrow();
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();

        return addCart(users, productOptionId, cartProductRequestDto, productOption, cartProductQuantity);
    }

    @Transactional
    public ApiResponse<?> getCart(String userUuid) {
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();

        List<CartProductResponseDto> cartProductResponseDtos = cartProductRepository.findByUsers(users)
                .stream()
                .map(CartProductResponseDto::new)
                .toList();

        IntStream.range(0, cartProductResponseDtos.size())
                .forEach(index -> cartProductResponseDtos.get(index).setId(index));

        return ApiResponse.of(SuccessStatus.CART_PRODUCT_GET_SUCCESS, cartProductResponseDtos);
    }

    @Transactional
    public ApiResponse<?> deleteCart(Long cartId) {
        CartProduct cartProduct = cartProductRepository.findById(cartId).orElseThrow();
        cartProductRepository.delete(cartProduct);
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_DELETE_SUCCESS, null);
    }

    @Transactional(readOnly = true)
    public ApiResponse<?> getCartOption(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();

        return ApiResponse.of(SuccessStatus.CART_PRODUCT_OPTION_SUCCESS,
                productOptionRepository.findByProduct(product)
                        .stream()
                        .map(CartProductOptionResponseDto::new)
                        .toList());
    }

    private ApiResponse<?> addCart(Users users, Long productOptionId, CartProductRequestDto cartProductRequestDto, ProductOption productOption, Integer productQuantity) {
        List<CartProduct> cartProducts = cartProductRepository.findByUsers(users);
        //이미 장바구니에 담긴 상품의 경우 개수 더해서 save하기
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProductOption().getProductOptionId().equals(productOptionId)) {
                cartProductRepository.save(CartProduct.builder()
                        .id(cartProduct.getId())
                        .cartProductQuantity(cartProduct.getCartProductQuantity() + productQuantity)
                        .productOption(cartProduct.getProductOption())
                        .cartIsChecked(cartProduct.getCartIsChecked())
                        .cartIsPinned(cartProduct.getCartIsPinned())
                        .productId(cartProduct.getProductId())
                        .brandId(cartProduct.getBrandId())
                        .users(cartProduct.getUsers())
                        .build());
                return ApiResponse.of(SuccessStatus.CART_PRODUCT_ADD_SUCCESS, null);
            }
        }
        //새롭게 담는 상품의 경우 새로 생성하기
        cartProductRepository.save(CartProduct.builder()
                .productId(cartProductRequestDto.getProductId())
                .brandId(cartProductRequestDto.getBrandId())
                .cartProductQuantity(productQuantity)
                .productOption(productOption)
                .cartIsChecked(false)
                .cartIsPinned(false)
                .users(users)
                .build());
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_ADD_SUCCESS, null);
    }
}