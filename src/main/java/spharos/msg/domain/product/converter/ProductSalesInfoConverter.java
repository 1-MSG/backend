package spharos.msg.domain.product.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.product.entity.ProductSalesInfo;

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
}
