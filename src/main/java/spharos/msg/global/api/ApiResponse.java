package spharos.msg.global.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import spharos.msg.global.api.code.BaseCode;
import spharos.msg.global.api.code.BaseErrorCode;

@JsonPropertyOrder({"isSuccess", "status", "data", "message"})
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> ApiResponse<T> onSuccess(T data) {
        return new ApiResponse<>(
                true, String.valueOf(HttpStatus.OK.value()), "메시지 수신 성공", data);
    }

    public static <T> ApiResponse<T> of(BaseCode code, T data) {
        return new ApiResponse<>(
                true,
                code.getReasonHttpStatus().getStatus(),
                code.getReasonHttpStatus().getMessage(),
                data);
    }

    public static <T> ApiResponse<T> onFailure(String status, String message, T data) {
        return new ApiResponse<>(false, status, message, data);
    }
    public static <T> ApiResponse<T> onFailure(BaseErrorCode errorCode, T data) {
        return new ApiResponse<>(
                false,
                errorCode.getReasonHttpStatus().getStatus(),
                errorCode.getReasonHttpStatus().getMessage(),
                data);
    }

}
