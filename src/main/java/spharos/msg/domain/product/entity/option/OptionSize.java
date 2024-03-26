package spharos.msg.domain.product.entity.option;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Getter
public class OptionSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_size_id")
    private Long id;

    @NotBlank
    private String productSize;
}
