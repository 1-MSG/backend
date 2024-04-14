package spharos.msg.domain.options.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionsNameDto {
    private String optionsType;
    private String optionsName;
    private int stock;
    public OptionsNameDto(Options options,int stock){
        this.optionsType = options.getOptionType();
        this.optionsName = options.getOptionName();
        this.stock = stock;
    }
}
