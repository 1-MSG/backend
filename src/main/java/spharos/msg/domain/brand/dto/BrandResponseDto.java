package spharos.msg.domain.brand.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.brand.entity.Brand;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class BrandResponseDto {
    private String brandName;
    public BrandResponseDto(Brand brand){
        this.brandName = brand.getBrandName();
    }
    public BrandResponseDto(String brandName){
        this.brandName=brandName;
    }
}
