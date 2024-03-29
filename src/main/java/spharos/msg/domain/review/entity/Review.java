package spharos.msg.domain.review.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.orders.entity.OrderDetail;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.global.entity.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 500)
    private String reviewComment;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private BigDecimal reviewStar;

    @NotNull
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Review(Long id, String reviewComment, BigDecimal reviewStar, Long userId,
        OrderDetail orderDetail, Product product) {
        this.id = id;
        this.reviewComment = reviewComment;
        this.reviewStar = reviewStar;
        this.userId = userId;
        this.orderDetail = orderDetail;
        this.product = product;
    }

    public void updateReview(String reviewComment, BigDecimal reviewStar) {
        this.reviewComment = reviewComment;
        this.reviewStar = reviewStar;
    }
}
