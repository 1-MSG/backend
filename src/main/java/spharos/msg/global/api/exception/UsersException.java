package spharos.msg.global.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import spharos.msg.global.api.code.BaseErrorCode;
import spharos.msg.global.api.dto.ErrorReasonDto;

@Getter
@RequiredArgsConstructor
public class UsersException extends RuntimeException{
    private final BaseErrorCode errorCode;

    public ErrorReasonDto getErrorReason() {
        return errorCode.getReason();
    }

    public ErrorReasonDto getErrorReasonHttpStatus() {
        return errorCode.getReasonHttpStatus();
    }
}

