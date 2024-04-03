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
    private Integer stock;
    public OptionsResponseDto(Options options, Integer stock){
        this.optionId = options.getId();
        this.optionName = options.getOptionName();
        this.optionType = options.getOptionType();
        this.optionLevel = options.getOptionLevel();
        this.stock = stock;
    }
    public OptionsResponseDto(ProductOption productOption){
        this.optionId = productOption.getOptions().getId();
        this.optionName = productOption.getOptions().getOptionName();
        this.optionType = productOption.getOptions().getOptionType();
        this.optionLevel = productOption.getOptions().getOptionLevel();
        this.stock = productOption.getStock();
    }
}