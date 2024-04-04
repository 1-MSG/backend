package spharos.msg.domain.options.entity;

import jakarta.persistence.*;
import lombok.Getter;
import spharos.msg.global.entity.BaseEntity;

import java.util.List;

@Entity
@Getter
public class Options extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "options_id")
    private Long id;
    private String optionType;
    private String optionName;
    private Integer optionLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_parent_id")
    private Options parent;
    @OneToMany(mappedBy = "parent")
    private List<Options> child;
}
