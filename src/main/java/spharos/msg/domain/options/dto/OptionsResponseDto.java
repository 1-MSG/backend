package spharos.msg.domain.options.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;
import spharos.msg.domain.product.entity.ProductOption;

@Getter
@NoArgsConstructor
public class OptionsResponseDto {
    private Long optionId;
    private int optionLevel;
    private String optionType;
    private String optionName;
    public OptionsResponseDto(Options options){
        this.optionId = options.getId();
        this.optionName = options.getOptionName();
        this.optionType = options.getOptionType();
        this.optionLevel = options.getOptionLevel();
    }
    public OptionsResponseDto(ProductOption productOption){
        this.optionId = productOption.getOption().getId();
        this.optionName = productOption.getOption().getOptionName();
        this.optionType = productOption.getOption().getOptionType();
        this.optionLevel = productOption.getOption().getOptionLevel();
    }
}
