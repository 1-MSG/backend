package spharos.msg.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import spharos.msg.domain.product.entity.option.OptionColor;
import spharos.msg.domain.product.entity.option.OptionEtc;
import spharos.msg.domain.product.entity.option.OptionSize;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ProductOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id")
    private Long productOptionId;

    @NotNull
    @Column(columnDefinition = "integer default 0")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_size_id")
    private OptionSize optionSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_color_id")
    private OptionColor optionColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_etc_id")
    private OptionEtc optionEtc;
}
