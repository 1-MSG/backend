package spharos.msg.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchResponse {

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchProductDtos {

        private String responseTime;

        @JsonProperty("isLast")
        private Boolean isLast = false;
        private List<SearchProductDto> searchProductDtos;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class SearchProductDto {

        private Long productId;

        @QueryProjection
        public SearchProductDto(Long productId) {
            this.productId = productId;
        }
    }

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchTextDto {

        private String productName;
    }
}
