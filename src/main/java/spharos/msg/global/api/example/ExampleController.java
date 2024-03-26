package spharos.msg.global.api.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.code.status.SuccessStatus;
import spharos.msg.global.converter.ExampleConverter;

@RestController
@RequestMapping("example")
public class ExampleController {
    @GetMapping("/signup")
    public ApiResponse<ExampleResponse.TestDto> testAPI1(@RequestParam Integer flag) {
        if (flag == 1) {
            throw new ExampleException(ErrorStatus.EXAMPLE_EXCEPTION);
        }
        ExampleResponse.TestDto dto = ExampleConverter.toExampleTestDto();
        return ApiResponse.of(SuccessStatus.SIGN_UP_SUCCESS_UNION, dto);
    }

    @GetMapping("/login")
    public ApiResponse<ExampleResponse.TestDto> testAPI2() {
        ExampleResponse.TestDto dto = ExampleConverter.toExampleTestDto();
        //return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS, dto);
        //Login_Success 통합/간편 구분하여 해당 내용 주석 처리
        return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS_UNION, dto);
    }

    @GetMapping
    public ApiResponse<ExampleResponse.TestDto> testAPI3() {
        ExampleResponse.TestDto dto = ExampleConverter.toExampleTestDto();
        return ApiResponse.onSuccess(dto);
    }
}
