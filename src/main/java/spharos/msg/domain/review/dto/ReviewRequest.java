package spharos.msg.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewRequest {
    @Getter
    @NoArgsConstructor
    public static class createDto {
        @Schema(description = "주문 상세 id")
        private Long orderDetailId;
        @Schema(description = "리뷰 별점")
        private BigDecimal reviewStar;
        @Schema(description = "리뷰 내용")
        private String reviewContent;

        @Builder
        private createDto(Long orderDetailId, BigDecimal reviewStar, String reviewContent){
            this.orderDetailId = orderDetailId;
            this.reviewStar = reviewStar;
            this.reviewContent = reviewContent;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class updateDto {
        @Schema(description = "리뷰 별점")
        private BigDecimal reviewStar;
        @Schema(description = "리뷰 내용")
        private String reviewContent;

        @Builder
        private updateDto(Long productId, Long orderDetailId, BigDecimal reviewStar, String reviewContent){
            this.reviewStar = reviewStar;
            this.reviewContent = reviewContent;
        }
    }

}
