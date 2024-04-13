package spharos.msg.domain.brand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.brand.entity.Brand;

@Getter
@NoArgsConstructor
public class BrandDetailDto {
    private String brandName;
    private int minDeliveryFee;

    public BrandDetailDto(String brandName, int minDeliveryFee) {
        this.brandName = brandName;
        this.minDeliveryFee = minDeliveryFee;
    }
    public BrandDetailDto(Brand brand) {
        this.brandName = brand.getBrandName();
        this.minDeliveryFee = brand.getMinDeliveryFee();
    }
}
