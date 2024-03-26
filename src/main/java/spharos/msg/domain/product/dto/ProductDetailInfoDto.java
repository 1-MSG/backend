package spharos.msg.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailInfoDto {

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
    @Schema(description = "대표이미지인덱스")
    private Short defaultImageIndex;
    @Schema(description = "무료배송최소금액")
    private Integer minDeliveryFee;
    @Schema(description = "리뷰건수")
    private Long reviewCount;
    @Schema(description = "상품 별점")
    private BigDecimal productStars;
    @Schema(description = "상품 옵션")
    private String productOptions;
    @Schema(description = "카테고리 대분류")
    private String ProductCategoryName;
    @Schema(description = "카테고리 중분류")
    private String ProductCategoryNameMid;
    @Schema(description = "상품 이미지 리스트")
    private String productImgUrlList;
    @Schema(description = "상품 상세이미지 리스트")
    private String productDetailImgUrlList;
    @Schema(description = "상품 리뷰 개수")
    private Long productReviewCount;
    @Schema(description = "상품 리뷰 리스트")
    private String productReviewList;

    @Builder
    private ProductDetailInfoDto(Long productId, String productBrand, String productName, Integer productPrice,
        BigDecimal discountRate,
        Short defaultImageIndex, Integer minDeliveryFee, Long reviewCount,
        BigDecimal productStars, String productOptions, String ProductCategoryName,
        String ProductCategoryNameMid, String productImgUrlList,
        String productDetailImgUrlList, Long productReviewCount, String productReviewList) {

        this.productId = productId;
        this.productBrand = productBrand;
        this.productName = productName;
        this.productPrice = productPrice;
        this.discountRate = discountRate;
        this.defaultImageIndex = defaultImageIndex;
        this.minDeliveryFee = minDeliveryFee;
        this.reviewCount = reviewCount;
        this.productStars = productStars;
        this.productOptions = productOptions;
        this.ProductCategoryName = ProductCategoryName;
        this.ProductCategoryNameMid = ProductCategoryNameMid;
        this.productImgUrlList = productImgUrlList;
        this.productDetailImgUrlList = productDetailImgUrlList;
        this.productReviewCount = productReviewCount;
        this.productReviewList = productReviewList;
    }
}