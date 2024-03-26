package spharos.msg.domain.cart.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class CartProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @Column(columnDefinition = "integer default 1")
    @NotNull
    private Integer cartProductQuantity;

    @Column(columnDefinition = "boolean default false")
    @NotNull
    private Boolean cartIsChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Builder
    public CartProduct(Integer cartProductQuantity, Boolean cartIsChecked, Users users, ProductOption productOption) {
        this.cartProductQuantity = cartProductQuantity;
        this.cartIsChecked = cartIsChecked;
        this.users = users;
        this.productOption = productOption;
    }


    public void addCartProductQuantity(int productQuantity) {
        this.cartProductQuantity += productQuantity;
    }

    public void updateCartProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public void addOneCartProductQuantity() {
        this.cartProductQuantity++;
    }

    public void minusOneCartProductQuantity() {
        this.cartProductQuantity--;
    }

    public void checkCartProduct() {
        this.cartIsChecked = true;
    }

    public void notCheckCartProduct() {
        this.cartIsChecked = false;
    }
}