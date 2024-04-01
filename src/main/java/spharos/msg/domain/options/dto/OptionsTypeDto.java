package spharos.msg.domain.options.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;

@Getter
@NoArgsConstructor
public class OptionsTypeDto {
    private String optionType;
    private Integer optionLevel;

    public OptionsTypeDto(Options option) {
        this.optionType = option.getOptionType();
        this.optionLevel = option.getOptionLevel();
    }
}