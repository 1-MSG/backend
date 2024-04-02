package spharos.msg.domain.options.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.entity.Options;

@Getter
@NoArgsConstructor
public class OptionsNameDto {
    private Long optionId;
    private String optionsName;
    public OptionsNameDto(Options options){
        this.optionId = options.getId();
        this.optionsName = options.getOptionName();
    }
}
