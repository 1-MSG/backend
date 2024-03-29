package spharos.msg.domain.product.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.category.entity.CategoryProduct;
import spharos.msg.global.entity.BaseEntity;
import spharos.msg.global.entity.Vendor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotBlank
    private String productName;

    @NotNull
    @Column(columnDefinition = "integer default 0")
    private Integer productPrice;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @NotNull
    private String productBrand;

    @NotNull
    @Column(columnDefinition = "integer default 0")
    private Short defaultImageIndex;

    @NotNull
    @Column(columnDefinition = "integer default 0")
    private Integer deliveryFee;

    @NotNull
    @Column(columnDefinition = "integer default 0")
    private Integer minDeliveryFee;

    @NotNull
    @Column(columnDefinition = "decimal default 0.0")
    private BigDecimal discountRate;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_sales_info_id")
    private ProductSalesInfo productSalesInfo;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryProduct> categoryProducts = new ArrayList<>();
}