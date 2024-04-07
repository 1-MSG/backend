package spharos.msg.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
