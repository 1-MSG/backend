package spharos.msg.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
public class ProductInfoDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_info_detail_id")
    private Long Id;

    @NotBlank
    @Column(name = "product_info_detail_content", length = 65535)
    private String productInfoDetailContent;

}
