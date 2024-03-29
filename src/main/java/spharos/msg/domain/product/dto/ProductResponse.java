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

        @Schema(description = "상품 id")
        private Long productId;
        @Schema(description = "상품 이름")
        private String productName;
        @Schema(description = "상품 정상가")
        private Integer productPrice;
        @Schema(description = "상품 이미지")
        private String image;
        @Schema(description = "상품 별점")
        private BigDecimal productStar;
        @Schema(description = "상품 할인율")
        private BigDecimal discountRate;

        @Builder
        private ProductInfo(Long productId, String productName, Integer productPrice, String image,
            BigDecimal productStar, BigDecimal discountRate) {

            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.image = image;
            this.productStar = productStar;
            this.discountRate = discountRate;
        }
    }
}
