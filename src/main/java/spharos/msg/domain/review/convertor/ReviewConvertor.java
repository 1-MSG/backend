package spharos.msg.domain.review.convertor;

import java.util.List;
import spharos.msg.domain.orders.entity.OrderProduct;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.review.dto.ReviewRequest;
import spharos.msg.domain.review.dto.ReviewResponse;
import spharos.msg.domain.review.dto.ReviewResponse.ReviewDetailDto;
import spharos.msg.domain.review.dto.ReviewResponse.ReviewsDto;
import spharos.msg.domain.review.entity.Review;

public class ReviewConvertor {

    public static Review toEntity(Product product, OrderProduct orderProduct,
        ReviewRequest.createDto reviewRequest, Long userId) {
        return Review.builder()
            .product(product)
            .orderProduct(orderProduct)
            .reviewStar(reviewRequest.getReviewStar())
            .reviewComment(reviewRequest.getReviewContent())
            .userId(userId)
            .build();
    }

    public static Review toEntity(Review review, ReviewRequest.updateDto reviewRequest) {
        return Review.builder()
            .id(review.getId())
            .product(review.getProduct())
            .orderProduct(review.getOrderProduct())
            .reviewStar(reviewRequest.getReviewStar())
            .reviewComment(reviewRequest.getReviewContent())
            .userId(review.getUserId())
            .build();
    }

    public static ReviewsDto toDto(List<ReviewDetailDto> reviews, boolean isLast) {
        return ReviewResponse.ReviewsDto.builder()
            .productReviews(reviews)
            .isLast(isLast)
            .build();
    }

    public static ReviewDetailDto toDto(Review review, String userName) {
        return ReviewResponse.ReviewDetailDto.builder()
            .reviewId(review.getId())
            .reviewStar(review.getReviewStar())
            .reviewCreatedat(review.getCreatedAt())
            .reviewContent(review.getReviewComment())
            .reviewer(userName)
            .build();
    }
}
