package spharos.msg.domain.options.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;

@Getter
@NoArgsConstructor
public class OptionTypeDto {
    private int optionLevel;
    private String optionType;
    public OptionTypeDto(Options options){
        this.optionLevel = options.getOptionLevel();
        this.optionType = options.getOptionType();
    }
    public OptionTypeDto(int optionLevel,String optionType){
        this.optionLevel = optionLevel;
        this.optionType = optionType;
    }
}
