package spharos.msg.domain.cart.service.V1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.cart.converter.CartConverter;
import spharos.msg.domain.cart.dto.CartProductRequestDto;
import spharos.msg.domain.cart.dto.CartProductResponseDto;
import spharos.msg.domain.cart.entity.CartProduct;
import spharos.msg.domain.cart.repository.CartProductRepository;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.util.List;
import java.util.stream.IntStream;

import static spharos.msg.domain.cart.converter.CartConverter.toEntity;

@Service
@RequiredArgsConstructor
public class CartProductServiceV1 {

    private final CartProductRepository cartProductRepository;
    private final ProductOptionRepository productOptionRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public ApiResponse<Void> addCartProduct(
            Long productOptionId,
            CartProductRequestDto cartProductRequestDto,
            int cartProductQuantity,
            String userUuid) {
        ProductOption productOption = productOptionRepository.findById(productOptionId)
                .orElseThrow();
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();

        return addCart(users, productOptionId, cartProductRequestDto, productOption,
                cartProductQuantity);
    }

    @Transactional
    public ApiResponse<List<CartProductResponseDto>> getCart(String userUuid) {
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();

        List<CartProductResponseDto> cartProductResponseDtos = cartProductRepository.findByUsers(users)
                .stream()
                .map(CartConverter::toDto)
                .toList();

        IntStream.range(0, cartProductResponseDtos.size())
                .forEach(index -> cartProductResponseDtos.get(index).setId(index));

        return ApiResponse.of(SuccessStatus.CART_PRODUCT_GET_SUCCESS, cartProductResponseDtos);
    }

    @Transactional
    public ApiResponse<Void> deleteCart(Long cartId) {
        CartProduct cartProduct = cartProductRepository.findById(cartId).orElseThrow();
        cartProductRepository.delete(cartProduct);
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_DELETE_SUCCESS, null);
    }

    private ApiResponse<Void> addCart(Users users, Long productOptionId,
                                      CartProductRequestDto cartProductRequestDto, ProductOption productOption,
                                      Integer productQuantity) {
        List<CartProduct> cartProducts = cartProductRepository.findByUsers(users);
        //이미 장바구니에 담긴 상품의 경우 개수 더해서 save하기
        for (CartProduct cartProduct : cartProducts) {
            if (cartProduct.getProductOption().getId().equals(productOptionId)) {
                cartProductRepository.save(CartConverter.toEntity(cartProduct, productQuantity));
                return ApiResponse.of(SuccessStatus.CART_PRODUCT_ADD_SUCCESS, null);
            }
        }
        //새롭게 담는 상품의 경우 새로 생성하기
        cartProductRepository.save(CartConverter.toEntity(users, cartProductRequestDto, productOption, productQuantity));
        return ApiResponse.of(SuccessStatus.CART_PRODUCT_ADD_SUCCESS, null);
    }
}