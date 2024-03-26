package spharos.msg.global.api.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.dto.ErrorReasonDto;
import spharos.msg.global.api.example.ExampleException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    /*
    사용자 커스텀 예외 처리 샘플
     */
    @ExceptionHandler(value = ExampleException.class)
    public ResponseEntity<Object> exampleException(ExampleException exception, WebRequest request) {
        ErrorReasonDto errorReasonHttpStatus = exception.getErrorReasonHttpStatus();
        ApiResponse<Object> responseBody = createResponseBody(
            errorReasonHttpStatus.getStatus(),
            errorReasonHttpStatus.getMessage(),
            null);

        return super.handleExceptionInternal(
            exception,
            responseBody,
            HttpHeaders.EMPTY,
            errorReasonHttpStatus.getHttpStatus(),
            request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> commonException(Exception e, WebRequest request) {
        //미처 처리 못한 Exception 전부 이쪽으로 넘와와서 확인용 Log 하나 생성
        log.info("No Handling Exception is = {}", e.getMessage());

        ErrorStatus errorStatus = ErrorStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<Object> responseBody = createResponseBody(errorStatus, null);
        return super.handleExceptionInternal(
            e, responseBody, HttpHeaders.EMPTY, errorStatus.getHttpStatus(), request);
    }

    /**
     * 주문 예외 발생
     */
    @ExceptionHandler
    public ResponseEntity<Object> orderException(OrderException e, WebRequest request) {
        ErrorReasonDto errorStatus = e.getReasonHttpStatus();
        ApiResponse<Object> responseBody = createResponseBody(
            errorStatus.getStatus(),
            errorStatus.getMessage(),
            null);

        return super.handleExceptionInternal(
            e, responseBody, HttpHeaders.EMPTY, errorStatus.getHttpStatus(), request);
    }

    /**
     * 회원 가입 시, Login Id 중복 시,
     * Users Common Exception
     */
    @ExceptionHandler
    public ResponseEntity<Object> usersException(UsersException exception,
        WebRequest request) {
        ErrorReasonDto errorReasonDto = exception.getErrorReasonHttpStatus();
        ApiResponse<Object> responseBody = createResponseBody(
            errorReasonDto.getStatus(),
            errorReasonDto.getMessage(),
            null);
        return super.handleExceptionInternal(
            exception,
            responseBody,
            HttpHeaders.EMPTY,
            errorReasonDto.getHttpStatus(),
            request);
    }

    /**
     * JwtToken Common Exception
     */
    @ExceptionHandler
    public ResponseEntity<Object> jwtTokenException(JwtTokenException exception,
        WebRequest request) {
        ErrorReasonDto errorReasonDto = exception.getErrorReasonHttpStatus();
        ApiResponse<Object> responseBody = createResponseBody(
            errorReasonDto.getStatus(),
            errorReasonDto.getMessage(),
            null);
        return super.handleExceptionInternal(
            exception,
            responseBody,
            HttpHeaders.EMPTY,
            errorReasonDto.getHttpStatus(),
            request);
    }

    /*
    데이터베이스 유효성 검증 실패시 호출
     */
    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("ConstrainViolation 추출 오류 발생"));

        ErrorStatus errorStatus = ErrorStatus.valueOf(errorMessage);
        ApiResponse<Object> responseBody = createResponseBody(errorStatus, null);

        return super.handleExceptionInternal(e, responseBody, HttpHeaders.EMPTY,
            errorStatus.getHttpStatus(), request);
    }

    /*
    잘못된 PathVariable인 경우 호출되는 얘외 처리
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request) {
        String requestURI = request.getContextPath();
        ErrorStatus errorStatus = ErrorStatus.INVALID_PATH_VARIABLE;
        ApiResponse<String> responseBody = createResponseBody(errorStatus, requestURI);
        return super.handleExceptionInternal(e, responseBody, HttpHeaders.EMPTY,
            errorStatus.getHttpStatus(), request);
    }

    /*
    잘못된 QueryString을 사용한 경우 호출되는 예외 처리
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {
        ErrorStatus errorStatus = ErrorStatus.INVALID_REQUEST_PARAM;
        ApiResponse<Map<String, String[]>> responseBody = createResponseBody(errorStatus,
            request.getParameterMap());
        return super.handleExceptionInternal(ex, responseBody, headers, status, request);
    }

    /*
    잘못된 url 경로로 API 요청을 한 경우 호출되는 예외 처리
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorStatus errorStatus = ErrorStatus.BAD_GATEWAY;
        ApiResponse<Object> responseBody = createResponseBody(errorStatus, null);
        return super.handleExceptionInternal(ex, responseBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request) {
        Map<String, String> errorArgs = new LinkedHashMap<>();
        ErrorStatus errorStatus = ErrorStatus.INVALID_PARAMETER_ERROR;

        e.getBindingResult()
            .getFieldErrors()
            .forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMessage = Optional.ofNullable(
                        fieldError.getDefaultMessage())
                    .orElse("");

                errorArgs.merge(fieldName, errorMessage,
                    (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", "
                        + newErrorMessage);
            });

        ApiResponse<Map<String, String>> responseBody = createResponseBody(errorStatus, errorArgs);
        return super.handleExceptionInternal(
            e, responseBody, HttpHeaders.EMPTY, errorStatus.getHttpStatus(), request);
    }
    /*
    사용자 커스텀 예외 추가
    @ExceptionHandler(value = 사용자 커스텀 예외 클래스)
     */

    private <T> ApiResponse<T> createResponseBody(ErrorStatus errorStatus, T data) {
        return ApiResponse.onFailure(
            errorStatus.getStatus(),
            errorStatus.getMessage(),
            data);
    }

    private <T> ApiResponse<T> createResponseBody(String errorStatus, String errorMessage, T data) {
        return ApiResponse.onFailure(
            errorStatus,
            errorMessage,
            data);
    }
}
