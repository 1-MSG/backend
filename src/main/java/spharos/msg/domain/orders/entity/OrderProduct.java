package spharos.msg.domain.orders.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProduct extends BaseEntity {

    @Id
    @Column(name = "order_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "bigint default 0")
    private Integer orderQuantity;

    private String productOption;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean orderIsCompleted;

    @NotNull
    private Long productPrice;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Column(columnDefinition = "decimal default 0.0")
    private BigDecimal discountRate;

    @Column
    @NotNull
    private Long productId;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "integer default 0")
    private Integer ordersDeliveryFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    private String productName;

    private String productImage;
}
