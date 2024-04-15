package spharos.msg.domain.review.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.orders.repository.OrderProductRepository;
import spharos.msg.domain.product.converter.ProductSalesInfoConverter;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductSalesInfo;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.domain.product.repository.ProductSalesInfoRepository;
import spharos.msg.domain.review.converter.ReviewConverter;
import spharos.msg.domain.review.dto.ReviewRequest;
import spharos.msg.domain.review.dto.ReviewResponse;
import spharos.msg.domain.review.entity.Review;
import spharos.msg.domain.review.repository.ReviewRepository;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewServiceV2 {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final ProductSalesInfoRepository productSalesInfoRepository;
    private final OrderProductRepository orderProductRepository;

    //리뷰 목록 가져 오기
    public ReviewResponse.ReviewsDto getReviews(Long productId, Pageable pageable) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("상품 찾을 수 없음"));
        Page<Review> reviewPage = reviewRepository.findByProduct(product, pageable);
        List<ReviewResponse.ReviewDetailDto> reviews = convertPageToList(reviewPage);
        boolean isLast = !reviewPage.hasNext();

        return ReviewConverter.toDto(reviews, isLast);
    }

    //특정 리뷰 가져 오기
    public ReviewResponse.ReviewDetailDto getReviewDetail(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new NotFoundException(reviewId + "해당하는 리뷰 찾을수 없음"));
        String userName = usersRepository.findById(review.getUserId())
            .map(Users::getUsername)
            .orElseThrow(() -> new NotFoundException(review.getUserId() + "해당하는 사용자 찾을수 없음"));

        return ReviewConverter.toDto(review, userName);
    }

    //리뷰 등록
    @Transactional
    public void saveReview(Long productId, ReviewRequest.createDto reviewRequest,
        String userUuid) {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException(productId + "해당하는 상품 찾을 수 없음"));
        ProductSalesInfo productSalesInfo = product.getProductSalesInfo();
        OrderProduct orderProduct = orderProductRepository.findById(
            reviewRequest.getOrderDetailId());
        Long userId = usersRepository.findByUuid(userUuid).map(Users::getId).orElseThrow();
        Long reviewCount = productSalesInfo.getReviewCount() + 1;
        //추후, 이미 작성된 리뷰 인지 확인 필요함

        reviewRepository.save(
            ReviewConverter.toEntity(product, orderProduct, reviewRequest, userId));
        //리뷰 추가 후 갱신된 별점
        BigDecimal newProductStar = calcProductStar(productSalesInfo.getProductStar(),
            productSalesInfo.getReviewCount(), reviewRequest.getReviewStar());
        //리뷰 건수와 별점을 새롭게 갱신
        productSalesInfoRepository.save(
            ProductSalesInfoConverter.toEntity(productSalesInfo, reviewCount, newProductStar));
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewRequest.updateDto reviewRequest) {

        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new NotFoundException(reviewId + "해당하는 리뷰 찾을 수 없음"));
        ProductSalesInfo productSalesInfo = review.getProduct().getProductSalesInfo();

        reviewRepository.save(
            ReviewConverter.toEntity(review, reviewRequest)
        );

        BigDecimal newAverageStar = recalcProductStar(productSalesInfo.getProductStar(),
            review.getReviewStar(), productSalesInfo.getReviewCount());
        BigDecimal newProductStar = calcProductStar(newAverageStar,
            productSalesInfo.getReviewCount() - 1,
            reviewRequest.getReviewStar());

        //리뷰 건수와 별점을 새롭게 갱신
        productSalesInfoRepository.save(
            ProductSalesInfoConverter.toEntity(productSalesInfo, newProductStar));
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
        Long reviewCount = productSalesInfo.getReviewCount() - 1;
        BigDecimal newProductStar = recalcProductStar(productSalesInfo.getProductStar(), review.getReviewStar(),
            productSalesInfo.getReviewCount());
        //리뷰 count와 별점 갱신
        productSalesInfoRepository.save(ProductSalesInfoConverter.toEntity(productSalesInfo, reviewCount, newProductStar));
    }

    private List<ReviewResponse.ReviewDetailDto> convertPageToList(Page<Review> reviewPage) {
        return reviewPage.getContent().stream()
            .map(review -> {

                //사용자 이름 가져 오기
                String userName = usersRepository.findById(review.getUserId())
                    .map(Users::getUsername)
                    .orElseThrow(
                        () -> new NotFoundException(review.getUserId() + "해당하는 사용자 찾을수 없음"));

                return ReviewConverter.toDto(review, userName);
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

        if(oldCount == 1) {
            return BigDecimal.ZERO;
        }

        BigDecimal oldSum = oldAverage.multiply(BigDecimal.valueOf(oldCount));
        BigDecimal newSum = oldSum.subtract(oldValue);
        long newCount = oldCount - 1;

        // 새로운 평균값을 계산
        return newSum.divide(BigDecimal.valueOf(newCount), 2, RoundingMode.HALF_UP);
    }
}
