package spharos.msg.domain.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
public class CountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "count_user_id")
    private Long id;

    @NotNull
    @Column(name = "out_count", nullable = false, columnDefinition = "bigint default 0")
    private Long outCount;
}
