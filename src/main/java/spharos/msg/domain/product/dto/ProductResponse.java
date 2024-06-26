package spharos.msg.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Setter(AccessLevel.NONE)
public class ProductResponse {

    @Getter
    public static class ProductInfoDto {

        @Schema(description = "브랜드id")
        private final Long brandId;
        @Schema(description = "브랜드명")
        private final String brandName;
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
        @Schema(description = "응답 시간")
        private final String responseTime;

        @Builder
        private ProductInfoDto(Long brandId, String brandName, String productName, Integer productPrice,
            BigDecimal productStar, Integer discountPrice, BigDecimal discountRate, Long reviewCount, String responseTime) {

            this.brandId = brandId;
            this.brandName = brandName;
            this.productName = productName;
            this.productPrice = productPrice;
            this.reviewCount = reviewCount;
            this.productStar = productStar;
            this.discountRate = discountRate;
            this.discountPrice = discountPrice;
            this.responseTime = responseTime;
        }
    }

    @Getter
    public static class ProductInfoAdminDto {

        @Schema(description = "상품id")
        private final Long productId;
        @Schema(description = "브랜드명")
        private final String brandName;
        @Schema(description = "상품 이름")
        private final String productName;
        @Schema(description = "상품 정상가")
        private final Integer productPrice;
        @Schema(description = "상품 리뷰 개수")
        private final Long reviewCount;
        @Schema(description = "상품 이미지")
        private final String productImage;
        @Schema(description = "응답 시간")
        private final String responseTime;

        @Builder
        private ProductInfoAdminDto(Long productId, String brandName, String productName, Integer productPrice, String productImage,
            Long reviewCount, String responseTime) {

            this.productId = productId;
            this.brandName = brandName;
            this.productName = productName;
            this.productPrice = productPrice;
            this.reviewCount = reviewCount;
            this.productImage = productImage;
            this.responseTime = responseTime;
        }
    }

    @Getter
    public static class ProductImageDto {

        @Schema(description = "이미지 설명")
        private final String productImageDescription;
        @Schema(description = "이미지 url")
        private final String productImageUrl;

        @Builder
        private ProductImageDto(String productImageDescription, String productImageUrl) {

            this.productImageDescription = productImageDescription;
            this.productImageUrl = productImageUrl;
        }
    }

    @Getter
    public static class ProductCategoryDto {

        @Schema(description = "대카테고리")
        private final String categoryLarge;
        @Schema(description = "중카테고리")
        private final String categoryMid;

        @Builder
        private ProductCategoryDto(String categoryLarge, String categoryMid) {

            this.categoryLarge = categoryLarge;
            this.categoryMid = categoryMid;
        }
    }

    @Getter
    public static class BestProductsDto {
        @Schema(description = "전체 상품 개수")
        private final Long totalProductCount;

        @Schema(description = "현재 페이지 개수")
        private final Integer nowPage;

        @Schema(description = "베스트 상품 리스트")
        private final List<ProductResponse.ProductIdDto> productList;

        @Schema(description = "마지막 페이지 여부")
        @JsonProperty("isLast")
        @Getter(AccessLevel.NONE)
        private final boolean isLast;

        @Builder
        private BestProductsDto(Long totalProductCount, Integer nowPage, List<ProductResponse.ProductIdDto> productList, boolean isLast) {

            this.totalProductCount = totalProductCount;
            this.nowPage = nowPage;
            this.productList = productList;
            this.isLast = isLast;
        }
    }

    @Getter
    public static class ProductIdDto {
        @Schema(description = "상품id")
        private final Long productId;

        @Builder
        @QueryProjection
        public ProductIdDto(Long productId) {

            this.productId = productId;
        }
    }

    @Getter
    public static class ProductDeliveryDto {
        @Schema(description = "기본배송비")
        private final Integer deliveryFee;
        @Schema(description = "무료배송 최소금액")
        private final Integer minDeliveryFee;

        @Builder
        @QueryProjection
        public ProductDeliveryDto(Integer deliveryFee, Integer minDeliveryFee) {

            this.deliveryFee = deliveryFee;
            this.minDeliveryFee = minDeliveryFee;
        }
    }

    @Getter
    public static class Best11Dto {

        @Schema(description = "상품 id")
        private final Long productId;
        @Schema(description = "상품 브랜드")
        private final String productBrand;
        @Schema(description = "상품 이름")
        private final String productName;
        @Schema(description = "상품 이미지")
        private final String productImage;
        @Schema(description = "상품 가격")
        private final Integer productPrice;
        @Schema(description = "판매량")
        private final Long productSellTotalCount;

        @Builder
        public Best11Dto(Long productId, String productBrand, String productName,
            String productImage, Integer productPrice, Long productSellTotalCount) {
            this.productId = productId;
            this.productBrand = productBrand;
            this.productName = productName;
            this.productImage = productImage;
            this.productPrice = productPrice;
            this.productSellTotalCount = productSellTotalCount;
        }

    }

}