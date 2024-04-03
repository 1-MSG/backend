package spharos.msg.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.global.entity.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class ProductSalesInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_sales_id")
    private Long id;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private BigDecimal productStar;

    @NotNull
    @Column(columnDefinition = "bigint default 0")
    private Long reviewCount;
    @NotNull
    @Column(columnDefinition = "bigint default 0")
    private Long productSellTotalCount;

    @Builder
    public ProductSalesInfo(Long id, BigDecimal productStar, Long reviewCount, Long productSellTotalCount) {
        this.id = id;
        this.productStar = productStar;
        this.reviewCount = reviewCount;
        this.productSellTotalCount = productSellTotalCount;
    }
}
