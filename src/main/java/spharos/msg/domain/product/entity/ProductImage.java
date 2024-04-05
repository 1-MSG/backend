package spharos.msg.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
@DynamicInsert
public class ProductImage extends BaseEntity {
    @Id
    @Column(name="product_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @Column(columnDefinition = "integer default 0")
    private Integer imageIndex;

    @NotBlank
    private String productImageUrl;

    @NotBlank
    private String productImageDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
