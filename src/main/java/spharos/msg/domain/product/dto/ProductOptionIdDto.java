package spharos.msg.domain.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spharos.msg.domain.product.entity.ProductOption;

@Getter
@RequiredArgsConstructor
public class ProductOptionIdDto {
    private Long optionId;

    public ProductOptionIdDto(ProductOption productOption) {
        this.optionId= productOption.getOption().getId();
    }
}
