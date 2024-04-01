package spharos.msg.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
public class ProductInfoDetail extends BaseEntity {
    @Id
    @Column(name = "product_info_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private String productInfoDetailContent;

}
