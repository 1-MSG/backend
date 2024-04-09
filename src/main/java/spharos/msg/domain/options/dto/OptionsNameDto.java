package spharos.msg.domain.options.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;

@Getter
@NoArgsConstructor
public class OptionsNameDto {
    private int optionLevel;
    private String optionsType;
    private String optionsName;
    public OptionsNameDto(Options options){
        this.optionLevel = options.getOptionLevel();
        this.optionsType = options.getOptionType();
        this.optionsName = options.getOptionName();
    }
}
