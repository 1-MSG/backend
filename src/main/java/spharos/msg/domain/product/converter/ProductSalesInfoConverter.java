package spharos.msg.domain.product.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.product.entity.ProductSalesInfo;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSalesInfoConverter {

    public static ProductSalesInfo toEntity(ProductSalesInfo productSalesInfo, int orderQuantity) {
        return ProductSalesInfo
            .builder()
            .id(productSalesInfo.getId())
            .productStar(productSalesInfo.getProductStar())
            .productSellTotalCount(productSalesInfo.getProductSellTotalCount() + orderQuantity)
            .reviewCount(productSalesInfo.getReviewCount())
            .build();
    }

    public static ProductSalesInfo toEntity(ProductSalesInfo productSalesInfo, Long reviewCount, BigDecimal newProductStar) {
        return ProductSalesInfo.builder()
            .id(productSalesInfo.getId())
            .productStar(newProductStar)
            .reviewCount(reviewCount)
            .productSellTotalCount(productSalesInfo.getProductSellTotalCount())
            .build();
    }

    public static ProductSalesInfo toEntity(ProductSalesInfo productSalesInfo, BigDecimal newProductStar) {
        return ProductSalesInfo.builder()
            .id(productSalesInfo.getId())
            .productStar(newProductStar)
            .reviewCount(productSalesInfo.getReviewCount())
            .productSellTotalCount(productSalesInfo.getProductSellTotalCount())
            .build();
    }
}
