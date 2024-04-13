package spharos.msg.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.likes.dto.IsLikesDto;
import spharos.msg.domain.likes.entity.Likes;
import spharos.msg.domain.likes.repository.LikesRepository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final ProductRepository productRepository;
    private final LikesRepository likesRepository;
    private final UsersRepository usersRepository;

    //상품에 좋아요 등록
    @Transactional
    public ApiResponse<Void> likeProduct(Long productId, String userUuid) {
        Product product = productRepository.findById(productId).orElseThrow();
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();

        Likes like = Likes.builder()
                .product(product)
                .users(users)
                .build();
        likesRepository.save(like);

        return ApiResponse.of(SuccessStatus.LIKES_SUCCESS, null);
    }

    //좋아요 삭제
    @Transactional
    public ApiResponse<Void> deleteLikeProduct(Long productId, String userUuid) {
        Product product = productRepository.findById(productId).orElseThrow();
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();

        Likes likes = likesRepository.findByUsersAndProduct(users, product).orElseThrow();
        likesRepository.delete(likes);

        return ApiResponse.of(SuccessStatus.LIKES_DELETE_SUCCESS, null);
    }

    @Transactional
    public ApiResponse<List<Long>> getProductLikeList(String userUuid) {
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();
        return ApiResponse.of(SuccessStatus.LIKES_LIST_GET_SUCCESS,
                likesRepository.findByUsers(users)
                        .stream()
                        .map(likes -> likes.getProduct().getId())
                        .toList());
    }

    @Transactional
    public ApiResponse<IsLikesDto> getProductLike(String userUuid, Long productId) {
        Users users = usersRepository.findByUuid(userUuid).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        return ApiResponse.of(SuccessStatus.LIKES_GET_SUCCESS,
               new IsLikesDto(likesRepository.existsByUsersAndProduct(users,product)));
    }
}