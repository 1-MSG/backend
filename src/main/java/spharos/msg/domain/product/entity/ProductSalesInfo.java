package spharos.msg.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import spharos.msg.global.entity.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
public class ProductSalesInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productInfoId;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private BigDecimal productStars;

    @NotNull
    @Column(columnDefinition = "bigint default 0")
    private Long reviewCount;
    @NotNull
    @Column(columnDefinition = "bigint default 0")
    private Long productSellTotalCount;

    //TODO : 연관관계 주인은 PRODUCT인가, 여기인가 상의해보기
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;

}
