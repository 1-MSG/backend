package spharos.msg.domain.review.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductSalesInfo;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.domain.product.repository.ProductSalesInfoRepository;
import spharos.msg.domain.review.dto.ReviewRequest;
import spharos.msg.domain.review.dto.ReviewResponse;
import spharos.msg.domain.review.entity.Review;
import spharos.msg.domain.review.repository.ReviewRepository;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final ProductSalesInfoRepository productSalesInfoRepository;

    //리뷰 목록 가져 오기
    @Transactional
    public ReviewResponse.ReviewsDto getReviews(Long productId, Pageable pageable) {
        //상품 가져오기
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("상품 찾을 수 없음"));
        //슬라이스 가져오기
        Page<Review> reviewPage = reviewRepository.findByProduct(product, pageable);
        //리스트로 변환
        List<ReviewResponse.ReviewDetailDto> reviews = convertToReviewList(reviewPage);
        //다음 페이지가 있는지 확인
        boolean isLast = !reviewPage.hasNext();

        return ReviewResponse.ReviewsDto.builder()
            .productReviews(reviews)
            .isLast(isLast)
            .build();
    }

    //특정 리뷰 가져 오기
    @Transactional
    public ReviewResponse.ReviewDetailDto getReviewDetail(Long reviewId) {
        //리뷰 객체 가져 오기
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new NotFoundException(reviewId + "해당하는 리뷰 찾을수 없음"));
        //사용자 이름 가져 오기
        String userName = usersRepository.findById(review.getUserId())
            .map(Users::getUsername)
            .orElseThrow(() -> new NotFoundException(review.getUserId() + "해당하는 사용자 찾을수 없음"));

        return ReviewResponse.ReviewDetailDto.builder()
            .reviewId(review.getId())
            .reviewStar(review.getReviewStar())
            .reviewCreatedat(review.getCreatedAt())
            .reviewContent(review.getReviewComment())
            .reviewer(userName)
            .build();
    }

    //리뷰 등록하기
    @Transactional
    public void saveReview(Long productId, ReviewRequest.createDto reviewRequest,
        String userUuid) {
        //상품 객체 가져오기
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(productId + "해당하는 상품 찾을 수 없음"));
        ProductSalesInfo productSalesInfo = product.getProductSalesInfo();
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
        //리뷰count와 별점을 새롭게 갱신
        productSalesInfoRepository.save(ProductSalesInfo.builder()
            .id(productSalesInfo.getId())
            .productStar(calcProductStar(productSalesInfo.getProductStar(),
                productSalesInfo.getReviewCount(), reviewRequest.getReviewStar()))
            .reviewCount(productSalesInfo.getReviewCount() + 1)
            .productSellTotalCount(productSalesInfo.getProductSellTotalCount())
            .build());
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewRequest.updateDto reviewRequest) {
        //id로 기존 리뷰 찾기
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new NotFoundException(reviewId + "해당하는 리뷰 찾을 수 없음"));
        // 리뷰가 속한 상품(productSalesInfo) 가져오기
        ProductSalesInfo productSalesInfo = review.getProduct().getProductSalesInfo();
        // 기존 별점 제거
        BigDecimal newAverageStar = recalcProductStar(productSalesInfo.getProductStar(),
            review.getReviewStar(), productSalesInfo.getReviewCount());
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

        //리뷰count와 별점을 새롭게 갱신
        productSalesInfoRepository.save(ProductSalesInfo.builder()
            .id(productSalesInfo.getId())
            .productStar(calcProductStar(newAverageStar, productSalesInfo.getReviewCount() - 1,
                reviewRequest.getReviewStar()))
            .reviewCount(productSalesInfo.getReviewCount())
            .productSellTotalCount(productSalesInfo.getProductSellTotalCount())
            .build());
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        //리뷰 가져오기
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new NotFoundException(reviewId + "해당하는 리뷰 찾을 수 없음"));
        //리뷰가 속한 상품(productSalesInfo) 가져오기
        ProductSalesInfo productSalesInfo = review.getProduct().getProductSalesInfo();
        //id와 일치 하는 리뷰 삭제
        reviewRepository.deleteById(reviewId);
        //리뷰 count와 별점 갱신
        productSalesInfoRepository.save(ProductSalesInfo.builder()
            .id(productSalesInfo.getId())
            .productStar(
                recalcProductStar(productSalesInfo.getProductStar(), review.getReviewStar(),
                    productSalesInfo.getReviewCount()))
            .reviewCount(productSalesInfo.getReviewCount() - 1)
            .productSellTotalCount(productSalesInfo.getProductSellTotalCount())
            .build());
    }

    private List<ReviewResponse.ReviewDetailDto> convertToReviewList(Slice<Review> reviewPage) {
        return reviewPage.getContent().stream()
            .map(review -> {

                //사용자 이름 가져 오기
                String userName = usersRepository.findById(review.getUserId())
                    .map(Users::getUsername)
                    .orElseThrow(() -> new NotFoundException(review.getUserId() + "해당하는 사용자 찾을수 없음"));

                return ReviewResponse.ReviewDetailDto.builder()
                    .reviewId(review.getId())
                    .reviewStar(review.getReviewStar())
                    .reviewCreatedat(review.getCreatedAt())
                    .reviewContent(review.getReviewComment())
                    .reviewer(userName)
                    .build();
            })
            .toList();
    }

    private BigDecimal calcProductStar(BigDecimal oldAverage, Long totalCount,
        BigDecimal newValue) {

        long newTotalCount = totalCount + 1;
        // 새로운 평균 별점 계산
        return oldAverage.multiply(BigDecimal.valueOf(totalCount))
            .add(newValue)
            .divide(BigDecimal.valueOf(newTotalCount), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal recalcProductStar(BigDecimal oldAverage, BigDecimal oldValue,
        Long oldCount) {

        BigDecimal oldSum = oldAverage.multiply(BigDecimal.valueOf(oldCount));
        BigDecimal newSum = oldSum.subtract(oldValue);
        long newCount = oldCount - 1;

        // 새로운 평균값을 계산합니다.
        return newSum.divide(BigDecimal.valueOf(newCount), 2, RoundingMode.HALF_UP);
    }
}
