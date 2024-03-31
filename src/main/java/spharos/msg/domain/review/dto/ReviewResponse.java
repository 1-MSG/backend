package spharos.msg.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ReviewResponse {
    @Data
    @Builder
    @Setter(AccessLevel.NONE)
    public static class ReviewsDto {
        private List<ReviewDetailDto> productReviews;

        @JsonProperty("isLast") // 'is'가 없어지는 문제 발생해 추가
        @Getter(AccessLevel.NONE) // 'Last'라는 필드 중복발생해 없애주기 위해 추가
        private boolean isLast;
    }
    @Data
    @Builder
    @Setter(AccessLevel.NONE)
    public static class ReviewDetailDto {
        private Long reviewId;
        private BigDecimal reviewStar;
        private LocalDateTime reviewCreatedat;
        private String reviewContent;
        private String reviewer;
    }
}
