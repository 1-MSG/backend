package spharos.msg.domain.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
public class CountPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "count_price_id")
    private Long id;

    @NotNull
    @Column(name = "total_sales_price", nullable = false, columnDefinition = "bigint default 0")
    private Long totalSalesPrice;

    @NotNull
    @Column(name = "total_delivery_fee", nullable = false, columnDefinition = "bigint default 0")
    private Long totalDeliveryFee;

    @NotNull
    @Column(name = "total_profit", nullable = false, columnDefinition = "bigint default 0")
    private Long totalProfit;
}
