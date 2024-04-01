package spharos.msg.domain.option.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.option.entity.Option;

@Getter
@NoArgsConstructor
public class OptionTypeDto {
    private String optionType;
    private Integer optionLevel;

    public OptionTypeDto(Option option) {
        this.optionType = option.getOptionType();
        this.optionLevel = option.getOptionLevel();
    }
}