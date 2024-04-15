package spharos.msg.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductResponseDto {

    private int id;
    private Long cartId;
    private Long brandId;
    private Long productId;
    private Integer cartProductQuantity;
    private Long productOptionId;
    private boolean cartIsChecked;
    private boolean cartIsPinned;

    public void setId(int id) {
        this.id = id;
    }
}
