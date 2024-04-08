package spharos.msg.domain.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.users.dto.request.OAuthRequest;
import spharos.msg.domain.users.dto.response.OAuthResponse;
import spharos.msg.domain.users.service.OAuthServiceImpl;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
@Tag(name = "OAuth", description = "간편 로그인 관련 API")
public class OAuthController {

    private final OAuthServiceImpl oAuthService;

    @Operation(summary = "간편 회원가입", description = "간편회원 회원가입")
    @PostMapping("/signup")
    public ApiResponse<Optional<OAuthResponse.EasyLoginResponseDto>> signUpEasy(
            @RequestBody OAuthRequest.EasySignUpRequestDto dto
    ) {
        return ApiResponse.of(SuccessStatus.SIGN_UP_SUCCESS_EASY,
                oAuthService.easySignUp(dto));
    }

    @Operation(summary = "간편 회원로그인", description = "간편회원 로그인")
    @PostMapping("/login")
    public ApiResponse<OAuthResponse.EasyLoginResponseDto> loginEasy(
            @RequestBody OAuthRequest.EasyLoginRequestDto dto
    ) {
        return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS_EASY,
                oAuthService.easyLogin(dto));
    }

    @Operation(summary = "간편 회원 아이디 찾기", description = "입력받은 이메일로 간편회원의 로그인 아이디를 조회합니다.")
    @GetMapping("/find-id/{email}")
    public ApiResponse<OAuthResponse.FindEasyIdResponseDto> findUserId(
            @PathVariable("email") String email) {
        return ApiResponse.of(SuccessStatus.FIND_LOGIN_ID_SUCCESS,
                oAuthService.findLoginEasyId(email));
    }
}
