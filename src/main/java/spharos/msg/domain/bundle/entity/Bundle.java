package spharos.msg.domain.bundle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
public class Bundle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_id")
    private Long id;

    @NotBlank
    private String bundleName;

    @NotBlank
    private String bundleImage;

    @NotBlank
    private String brandName;
}
