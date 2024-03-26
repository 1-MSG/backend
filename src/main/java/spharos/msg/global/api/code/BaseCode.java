package spharos.msg.global.api.code;

import spharos.msg.global.api.dto.ReasonDto;

public interface BaseCode {
    ReasonDto getReason();

    ReasonDto getReasonHttpStatus();
}
