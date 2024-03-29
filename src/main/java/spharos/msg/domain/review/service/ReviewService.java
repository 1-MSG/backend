package spharos.msg.domain.review.service;

import static spharos.msg.global.api.code.status.ErrorStatus.REVIEW_DELETE_FAIL;
import static spharos.msg.global.api.code.status.ErrorStatus.REVIEW_READ_FAIL;
import static spharos.msg.global.api.code.status.ErrorStatus.REVIEW_SAVE_FAIL;
import static spharos.msg.global.api.code.status.ErrorStatus.REVIEW_UPDATE_FAIL;
import static spharos.msg.global.api.code.status.SuccessStatus.REVIEW_DELETE_SUCCESS;
import static spharos.msg.global.api.code.status.SuccessStatus.REVIEW_READ_SUCCESS;
import static spharos.msg.global.api.code.status.SuccessStatus.REVIEW_SAVE_SUCCESS;
import static spharos.msg.global.api.code.status.SuccessStatus.REVIEW_UPDATE_SUCCESS;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.domain.review.dto.ReviewRequest;
import spharos.msg.domain.review.dto.ReviewResponse;
import spharos.msg.domain.review.entity.Review;
import spharos.msg.domain.review.repository.ReviewRepository;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.ApiResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public ApiResponse<?> getReviewDetail(Long reviewId) {
        try {
            //리뷰 객체 가져 오기
            Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(reviewId + "해당하는 리뷰 찾을수 없음"));
            //사용자 이름 가져 오기
            String userName = usersRepository.findById(review.getUserId())
                .map(Users::getUsername)
                .orElse("탈퇴한 회원입니다");

            return ApiResponse.of(REVIEW_READ_SUCCESS, ReviewResponse.ReviewDetail.builder()
                .reviewId(review.getId())
                .reviewStar(review.getReviewStar())
                .reviewCreatedat(review.getCreatedAt())
                .reviewContent(review.getReviewComment())
                .reviewer(userName)
                .build());
        } catch (Exception e) {
            return ApiResponse.onFailure(REVIEW_READ_FAIL, null);
        }
    }

    @Transactional
    public ApiResponse<?> saveReview(Long productId, ReviewRequest.createDto reviewRequest,
        String userUuid) {
        try {//상품 객체 가져오기
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(productId + "해당하는 상품 찾을 수 없음"));
            //유저 id 가져오기
            Long userId = usersRepository.findByUuid(userUuid)
                .map(Users::getId)
                .orElseThrow();

            //추후, 이미 작성된 리뷰 인지 확인 필요함
            //저장
            reviewRepository.save(Review.builder()
                .product(product)
                .reviewStar(reviewRequest.getReviewStar())
                .reviewComment(reviewRequest.getReviewContent())
                .userId(userId)
                .build());
            return ApiResponse.of(REVIEW_SAVE_SUCCESS, null);
        } catch (
            Exception e) {
            log.error("리뷰 저장 중 에러 발생 " + e.getMessage());
            return ApiResponse.onFailure(REVIEW_SAVE_FAIL, null);
        }
    }

    @Transactional
    public ApiResponse<?> updateReview(Long reviewId, ReviewRequest.updateDto reviewRequest) {
        try {
            //id로 기존 리뷰 찾기
            Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(reviewId + "해당하는 리뷰 찾을 수 없음"));
            //리뷰 업데이트
            reviewRepository.save(
                Review.builder()
                    .id(review.getId())
                    .product(review.getProduct())
                    .reviewStar(reviewRequest.getReviewStar())
                    .reviewComment(reviewRequest.getReviewContent())
                    .userId(review.getUserId())
                    .build()
            );
            return ApiResponse.of(REVIEW_UPDATE_SUCCESS, null);
        } catch (Exception e) {
            log.error("에러 발생 " + e.getMessage());
            return ApiResponse.onFailure(REVIEW_UPDATE_FAIL, null);
        }
    }

    @Transactional
    public ApiResponse<?> deleteReview(Long reviewId) {
        try {
            //id와 일치 하는 리뷰 삭제
            reviewRepository.deleteById(reviewId);
            return ApiResponse.of(REVIEW_DELETE_SUCCESS, null);
        } catch (Exception e) {
            return ApiResponse.onFailure(REVIEW_DELETE_FAIL, null);
        }
    }
}
