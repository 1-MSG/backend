package spharos.msg.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.review.dto.ReviewRequest;
import spharos.msg.domain.review.dto.ReviewResponse;
import spharos.msg.domain.review.service.ReviewServiceV1;
import spharos.msg.global.api.ApiResponse;

import static spharos.msg.global.api.code.status.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-review")
@Slf4j
@Tag(name="Review V1",description = "리뷰 관련 API")
public class ReviewControllerV1 {

    private final ReviewServiceV1 reviewService;

    @Operation(summary = "상품 리뷰 목록 조회",
        description = "id로 상품 리뷰 목록를 조회합니다")
    @GetMapping("/{productId}/reviews")
    public ApiResponse<ReviewResponse.ReviewsDto> getReviews(
        @PathVariable("productId") Long productId,
        @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return ApiResponse.of(REVIEW_READ_SUCCESS,reviewService.getReviews(productId,pageable));
    }

    @Operation(summary = "상품 리뷰 상세 조회",
    description = "id로 특정 리뷰를 조회합니다")
    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewResponse.ReviewDetailDto> getReviewDetail(
        @PathVariable("reviewId") Long reviewId
    ){
     return ApiResponse.of(REVIEW_READ_SUCCESS,reviewService.getReviewDetail(reviewId));
    }
    @Operation(summary = "상품 리뷰 등록",
        description = "리뷰가 가능한 주문상세에 대해 상품 리뷰를 등록합니다")
    @PostMapping("/{productId}")
    public ApiResponse<Void> saveReview(
        @PathVariable("productId") Long productId,
        @RequestBody ReviewRequest.createDto reviewRequest,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        reviewService.saveReview(productId, reviewRequest,userDetails.getUsername());
    return ApiResponse.of(REVIEW_SAVE_SUCCESS,null);
    }

    @Operation(summary = "상품 리뷰 수정",
    description = "리뷰 id와 일치하는 리뷰의 별점과 내용을 수정합니다")
    @PatchMapping("/{reviewId}")
    public ApiResponse<Void> updateReview(
        @PathVariable("reviewId") Long reviewId,
        @RequestBody ReviewRequest.updateDto reviewRequest
    ){
        reviewService.updateReview(reviewId, reviewRequest);
        return ApiResponse.of(REVIEW_UPDATE_SUCCESS,null);
    }

    @Operation(summary = "상품 리뷰 삭제",
        description = "리뷰 id와 일치하는 리뷰를 삭제합니다")
    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(
        @PathVariable("reviewId") Long reviewId
    ){
        reviewService.deleteReview(reviewId);
        return ApiResponse.of(REVIEW_DELETE_SUCCESS,null);
    }
}
