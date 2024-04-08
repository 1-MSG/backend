package spharos.msg.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter(AccessLevel.NONE)
public class ProductResponse {

    @Getter
    public static class ProductInfoDto {

        @Schema(description = "상품 브랜드")
        private final String productBrand;
        @Schema(description = "상품 이름")
        private final String productName;
        @Schema(description = "상품 정상가")
        private final Integer productPrice;
        @Schema(description = "상품 할인율")
        private final BigDecimal discountRate;
        @Schema(description = "상품 할인가")
        private final Integer discountPrice;
        @Schema(description = "상품 별점")
        private final BigDecimal productStar;
        @Schema(description = "상품 리뷰 개수")
        private final Long reviewCount;
        @Schema(description = "상품 이미지")
        private final String productImage;
        @Schema(description = "응답 시간")
        private final String responseTime;

        @Builder
        private ProductInfoDto(String productBrand, String productName, Integer productPrice, String productImage,
            BigDecimal productStar, Integer discountPrice, BigDecimal discountRate, Long reviewCount, String responseTime) {

            this.productBrand = productBrand;
            this.productName = productName;
            this.productPrice = productPrice;
            this.reviewCount = reviewCount;
            this.productStar = productStar;
            this.discountRate = discountRate;
            this.discountPrice = discountPrice;
            this.productImage = productImage;
            this.responseTime = responseTime;
        }
    }

    @Getter
    public static class ProductImage {

        @Schema(description = "이미지 설명")
        private final String productImageDescription;
        @Schema(description = "이미지 url")
        private final String productImageUrl;

        @Builder
        private ProductImage(String productImageDescription, String productImageUrl) {

            this.productImageDescription = productImageDescription;
            this.productImageUrl = productImageUrl;
        }
    }

    @Getter
    public static class ProductCategory {

        @Schema(description = "대카테고리")
        private final String categoryLarge;
        @Schema(description = "중카테고리")
        private final String categoryMid;

        @Builder
        private ProductCategory(String categoryLarge, String categoryMid) {

            this.categoryLarge = categoryLarge;
            this.categoryMid = categoryMid;
        }
    }

    @Getter
    public static class BestProductsDto {
        @Schema(description = "베스트 상품 리스트")
        private final List<ProductResponse.ProductIdDto> productList;

        @Schema(description = "마지막 페이지 여부")
        @JsonProperty("isLast")
        @Getter(AccessLevel.NONE)
        private final boolean isLast;

        @Builder
        private BestProductsDto(List<ProductResponse.ProductIdDto> productList, boolean isLast) {

            this.productList = productList;
            this.isLast = isLast;
        }
    }

    @Getter
    public static class ProductIdDto {
        @Schema(description = "상품id")
        private final Long productId;

        @Builder
        private ProductIdDto(Long productId) {

            this.productId = productId;
        }
    }
}
