package spharos.msg.domain.options.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;

@Getter
@NoArgsConstructor
public class OptionsNameDto {
    private int optionLevel;
    private String optionsName;
    public OptionsNameDto(Options options){
        this.optionLevel = options.getOptionLevel();
        this.optionsName = options.getOptionName();
    }
}
