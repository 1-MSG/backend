package spharos.msg.global.api.example;

import spharos.msg.global.api.code.BaseErrorCode;

public class ExampleExceptionHandler extends ExampleException {
    public ExampleExceptionHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
