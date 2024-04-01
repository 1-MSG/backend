package spharos.msg.domain.option.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class OptionResponse {

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class OptionTypeDto {
        private String optionType;
        private Integer optionLevel;
    }
}
