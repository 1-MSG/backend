package spharos.msg.global.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spharos.msg.global.api.code.BaseErrorCode;
import spharos.msg.global.api.dto.ErrorReasonDto;

@Getter
@RequiredArgsConstructor
public class OrderException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public ErrorReasonDto getReason() {
        return errorCode.getReason();
    }

    public ErrorReasonDto getReasonHttpStatus() {
        return errorCode.getReasonHttpStatus();
    }
}
