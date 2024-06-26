package spharos.msg.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import spharos.msg.domain.options.entity.Options;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.OrderException;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class ProductOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id")
    private Long id;

    @NotNull
    @Column(columnDefinition = "integer default 0")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "options_id")
    private Options options;

    public ProductOption(Long id, @NotNull Integer stock, Product product,
        Options options) {
        validateStock(stock);
        this.id = id;
        this.stock = stock;
        this.product = product;
        this.options = options;
    }

    private void validateStock(int stock) {
        if (stock < 0) {
            throw new OrderException(ErrorStatus.INVALID_STOCK);
        }
    }
}
