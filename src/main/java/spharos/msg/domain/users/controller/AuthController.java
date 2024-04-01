package spharos.msg.domain.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.users.dto.request.DuplicationCheckRequestDto;
import spharos.msg.domain.users.dto.request.LoginRequestDto;
import spharos.msg.domain.users.dto.request.SignUpRequestDto;
import spharos.msg.domain.users.dto.response.FindIdOutDto;
import spharos.msg.domain.users.dto.response.LoginOutDto;
import spharos.msg.domain.users.dto.response.ReissueOutDto;
import spharos.msg.domain.users.service.AuthService;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "사용자 인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "통합회원가입", description = "통합회원 회원가입")
    @PostMapping("/signup/union")
    public ApiResponse<?> signUpUnion(@RequestBody SignUpRequestDto signUpRequestDto) {
        authService.signUp(signUpRequestDto);
        return ApiResponse.of(SuccessStatus.SIGN_UP_SUCCESS_UNION, null);
    }

    @Operation(summary = "로그인", description = "통합회원 로그인")
    @PostMapping("/login/union")
    public ApiResponse<LoginOutDto> loginUnion(
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        LoginOutDto login = authService.login(loginRequestDto);
        return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS_UNION, login);
    }

    @Operation(summary = "로그아웃", description = "로그인 회원 로그아웃")
    @DeleteMapping("/logout")
    public ApiResponse<?> logout(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        authService.logout(userDetails.getUsername());
        return ApiResponse.of(SuccessStatus.LOGOUT_SUCCESS, null);
    }

    @Operation(summary = "Reissue Token", description = "Access Token 재발급")
    @GetMapping("/reissue")
    public ApiResponse<?> reissueToken(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        ReissueOutDto reissueOutDto = authService.reissueToken(userDetails.getUsername());
        return ApiResponse.of(SuccessStatus.TOKEN_REISSUE_COMPLETE, reissueOutDto);
    }

    @Operation(summary = "아이디 중복확인", description = "입력받은 아이디의 중복 여부를 확인합니다.")
    @PostMapping("/check-duplicate-id")
    public ApiResponse<?> duplicateCheckLoginId(
            @RequestBody DuplicationCheckRequestDto duplicationCheckRequestDto
    ) {
        authService.duplicateCheckLoginId(duplicationCheckRequestDto);
        return ApiResponse.of(SuccessStatus.DUPLICATION_CHECK_SUCCESS, null);
    }

    @Operation(summary = "아이디 찾기", description = "입력받은 이메일로 로그인 아이디를 조회합니다.")
    @GetMapping("/find-id")
    public ApiResponse<FindIdOutDto> findUserId(@RequestParam(name = "email") String email) {
        FindIdOutDto loginUnionId = authService.findLoginUnionId(email);
        return ApiResponse.of(SuccessStatus.FIND_LOGIN_ID_SUCCESS, loginUnionId);
    }
}
