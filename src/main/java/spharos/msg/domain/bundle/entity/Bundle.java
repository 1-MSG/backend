package spharos.msg.domain.bundle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import spharos.msg.global.entity.BaseEntity;

@Entity
public class Bundle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_id")
    private Long id;

    @NotNull
    private String bundleName;

    @NotNull
    private Long vendorId;
}
