package spharos.msg.domain.options.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionsResponseDto {
    private Long productOptionId;
    private Long optionId;
    private int optionLevel;
    private String optionType;
    private String optionName;
    private Integer stock;
}