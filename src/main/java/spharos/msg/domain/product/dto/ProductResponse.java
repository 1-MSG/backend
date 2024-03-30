package spharos.msg.domain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
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
    @Data
    @Builder
    public static class HomeCosmeRandomFoodDto {

        private List<ProductInfo> cosmeticList;
        private List<ProductInfo> randomList;
        private List<ProductInfo> foodList;
    }
    @Data
    @Builder
    public static class HomeFashionDto {

        private List<ProductInfo> fashionList;
    }

    @Getter
    @Setter
    public static class ProductInfo {

        @Schema(description = "상품 브랜드")
        private String productBrand;
        @Schema(description = "상품 이름")
        private String productName;
        @Schema(description = "상품 정상가")
        private Integer productPrice;
        @Schema(description = "상품 할인율")
        private BigDecimal discountRate;
        @Schema(description = "상품 할인가")
        private Integer discountPrice;
        @Schema(description = "상품 별점")
        private BigDecimal productStar;
        @Schema(description = "상품 리뷰 개수")
        private Integer reviewCount;

        @Builder
        private ProductInfo(String productBrand, String productName, Integer productPrice, String image,
            BigDecimal productStar, Integer discountPrice, BigDecimal discountRate, Integer reviewCount) {

            this.productBrand = productBrand;
            this.productName = productName;
            this.productPrice = productPrice;
            this.reviewCount = reviewCount;
            this.productStar = productStar;
            this.discountRate = discountRate;
            this.discountPrice = discountPrice;
        }
    }

    @Getter
    @Setter
    public static class ProductImage {

        @Schema(description = "이미지 설명")
        private String productImageDescription;
        @Schema(description = "이미지 url")
        private String productImageUrl;

        @Builder
        private ProductImage(String productImageDescription, String productImageUrl) {

            this.productImageDescription = productImageDescription;
            this.productImageUrl = productImageUrl;
        }
    }

    @Getter
    @Setter
    public static class ProductCategory {

        @Schema(description = "대카테고리")
        private String categoryLarge;
        @Schema(description = "중카테고리")
        private String categoryMid;

        @Builder
        private ProductCategory(String categoryLarge, String categoryMid) {

            this.categoryLarge = categoryLarge;
            this.categoryMid = categoryMid;
        }
    }
}
