package spharos.msg.global.api.code;

import spharos.msg.global.api.dto.ErrorReasonDto;

public interface BaseErrorCode {
    ErrorReasonDto getReason();

    ErrorReasonDto getReasonHttpStatus();
}
