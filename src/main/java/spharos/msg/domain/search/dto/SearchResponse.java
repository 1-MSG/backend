package spharos.msg.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class SearchProductDto {

        Long productId;
        String productName;
        Integer productPrice;
        @JsonProperty("isLike")
        Boolean isLike;
        String image;
        BigDecimal productStar;
        BigDecimal discountRate;
    }
}
