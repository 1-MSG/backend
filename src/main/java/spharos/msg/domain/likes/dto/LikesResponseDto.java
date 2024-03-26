package spharos.msg.domain.likes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.likes.entity.Likes;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class LikesResponseDto {
    private Long productId;
    private String productName;
    private int productPrice;
    //todo 해당 부분 구현되고 데이터 들어가면 주석 해제하고 끌어올 예정입니다
//    private BigDecimal productStar;
//    private Long commentCount;
    private BigDecimal discountRate;
    private int discountedPrice;
    private boolean isLike;

    public LikesResponseDto(Likes likes,boolean isLike) {
        this.productId = likes.getProduct().getId();
        this.productName = likes.getProduct().getProductName();
        this.productPrice = likes.getProduct().getProductPrice();
//        this.productStar = likes.getProduct().getProductSalesInfo().getProductStars();
//        this.commentCount = likes.getProduct().getProductSalesInfo().getReviewCount();
        this.discountRate = likes.getProduct().getDiscountRate();
        this.discountedPrice = likes.getProduct().getProductPrice()*(100-Integer.parseInt(String.valueOf(likes.getProduct().getDiscountRate())))/100;
        this.isLike = isLike;
    }
}
