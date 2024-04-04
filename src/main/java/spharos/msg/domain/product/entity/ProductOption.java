package spharos.msg.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.OrderException;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Options option;

    public ProductOption(Long id, @NotNull Integer stock, Product product,
        Options option) {
        validateStock(stock);
        this.id = id;
        this.stock = stock;
        this.product = product;
        this.option = option;
    }

    public void validateStock(int stock) {
        if (stock < 0) {
            throw new OrderException(ErrorStatus.INVALID_STOCK);
        }
    }
}
