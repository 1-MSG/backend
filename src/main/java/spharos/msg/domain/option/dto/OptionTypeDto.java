package spharos.msg.domain.option.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.option.entity.Options;

@Getter
@NoArgsConstructor
public class OptionTypeDto {
    private String optionType;
    private Integer optionLevel;

    public OptionTypeDto(Options option) {
        this.optionType = option.getOptionType();
        this.optionLevel = option.getOptionLevel();
    }
}