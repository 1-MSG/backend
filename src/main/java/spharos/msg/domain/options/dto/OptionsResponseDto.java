package spharos.msg.domain.options.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;

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
}