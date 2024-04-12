package spharos.msg.domain.users.controller.v1;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.users.dto.request.UsersRequest;
import spharos.msg.domain.users.dto.response.UsersResponse;
import spharos.msg.domain.users.service.UsersService;
import spharos.msg.domain.users.service.impl.v1.UsersServiceImplV1;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User V1", description = "회원 관련 API")
public class UsersControllerV1 {

    private final UsersServiceImplV1 usersService;

    @Operation(summary = "이메일 발송",
            description = "이메일 중복 확인 후, 이메일 인증을 위한 이메일을 발송 합니다.")
    @PostMapping("/send-mail")
    public ApiResponse<UsersResponse.EmailResponseDto> sendEmail(
            @RequestBody UsersRequest.EmailSendDto dto
    ) {
        usersService.duplicateCheckEmail(dto);
        return ApiResponse.of(SuccessStatus.EMAIL_SEND_SUCCESS,
                usersService.sendMail(dto));
    }

    @Operation(summary = "이메일 인증 확인",
            description = "입력받은 Secret key 로 인증을 진행 합니다.")
    @PostMapping("/authenticate-email")
    public ApiResponse<Void> authenticateEmail(
            @RequestBody UsersRequest.EmailAuthenticationDto dto
    ) {
        usersService.authenticateEmail(dto);
        return ApiResponse.of(SuccessStatus.EMAIL_AUTH_SUCCESS, null);
    }
}