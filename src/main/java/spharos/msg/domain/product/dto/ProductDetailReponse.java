package spharos.msg.domain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter(AccessLevel.NONE)
public class ProductDetailReponse {

    @Getter
    @Setter
    public static class ProductDetailDto {

        @Schema(description = "상품 id")
        private Long productId;
        @Schema(description = "상품 브랜드")
        private String productBrand;
        @Schema(description = "상품 이름")
        private String productName;
        @Schema(description = "상품 가격")
        private Integer productPrice;
        @Schema(description = "상품 할인율")
        private BigDecimal discountRate;
        //        @Schema(description = "대표이미지 인덱스")
//        private Short defaultImageIndex;
        @Schema(description = "기본 배송비")
        private Integer productDeliveryFee;
        @Schema(description = "무료배송 최소금액")
        private Integer minDeliveryFee;
        @Schema(description = "상품 별점")
        private BigDecimal productStars;
        @Schema(description = "상품 옵션")
        private List<OptionDetail> productOptions;
        @Schema(description = "카테고리 대분류")
        private String ProductCategoryName;
        @Schema(description = "카테고리 중분류")
        private String ProductCategoryNameMid;
        @Schema(description = "상품 이미지 리스트")
        private List<String> productImgUrlList;
        @Schema(description = "상품 상세이미지 리스트")
        private List<String> productDetailImgUrlList;
        @Schema(description = "상품 리뷰 개수")
        private Long productReviewCount;
        @Schema(description = "상품 리뷰 리스트")
        private List<ReviewDetail> productReviewList;

        @Builder
        private ProductDetailDto(Long productId, String productBrand, String productName,
            Integer productPrice,
            BigDecimal discountRate,
//            Short defaultImageIndex,
            Integer productDeliveryFee, Integer minDeliveryFee,
            BigDecimal productStars, List<OptionDetail> productOptions, String ProductCategoryName,
            String ProductCategoryNameMid, List<String> productImgUrlList,
            List<String> productDetailImgUrlList, Long productReviewCount,
            List<ReviewDetail> productReviewList) {

            this.productId = productId;
            this.productBrand = productBrand;
            this.productName = productName;
            this.productPrice = productPrice;
            this.discountRate = discountRate;
//            this.defaultImageIndex = defaultImageIndex;
            this.minDeliveryFee = minDeliveryFee;
            this.productDeliveryFee = productDeliveryFee;
            this.productReviewCount = productReviewCount;
            this.productStars = productStars;
            this.productOptions = productOptions;
            this.ProductCategoryName = ProductCategoryName;
            this.ProductCategoryNameMid = ProductCategoryNameMid;
            this.productImgUrlList = productImgUrlList;
            this.productDetailImgUrlList = productDetailImgUrlList;
            this.productReviewList = productReviewList;
        }
    }

    @Getter
    @Setter
    public static class OptionDetail {

        private Long optionId;
        private String optionColor;
        private String optionSize;
        private String optionEtc;

        @Builder
        private OptionDetail(Long optionId, String optionColor, String optionSize,
            String optionEtc) {
            this.optionId = optionId;
            this.optionColor = optionColor;
            this.optionSize = optionSize;
            this.optionEtc = optionEtc;
        }
    }

    @Getter
    @Setter
    public static class ReviewDetail {

        private Long reviewId;
        private BigDecimal reviewStar;
        private LocalDateTime reviewCreated;
        private String reviewContent;
        private String reviewer;
        private List<String> reviewImgUrlList;

        @Builder
        private ReviewDetail(Long reviewId, BigDecimal reviewStar, LocalDateTime reviewCreated,
            String reviewContent, String reviewer, List<String> reviewImgUrlList) {
            this.reviewId = reviewId;
            this.reviewStar = reviewStar;
            this.reviewCreated = reviewCreated;
            this.reviewContent = reviewContent;
            this.reviewer = reviewer;
            this.reviewImgUrlList = reviewImgUrlList;
        }
    }
}