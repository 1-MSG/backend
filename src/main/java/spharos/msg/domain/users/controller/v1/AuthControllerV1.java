package spharos.msg.domain.users.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.users.dto.request.AuthRequest;
import spharos.msg.domain.users.dto.response.AuthResponse;
import spharos.msg.domain.users.service.impl.v1.AuthServiceImplV1;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth V1", description = "사용자 인증 관련 API")
public class AuthControllerV1 {

    private final AuthServiceImplV1 authService;

    @Operation(summary = "통합회원가입", description = "통합회원 회원가입")
    @PostMapping("/signup")
    public ApiResponse<Void> signUpUnion(
            @RequestBody AuthRequest.SignUpRequestDto dto) {
        authService.signUp(dto);
        return ApiResponse.of(SuccessStatus.SIGN_UP_SUCCESS_UNION, null);
    }

    @Operation(summary = "로그인", description = "통합회원 로그인")
    @PostMapping("/login")
    public ApiResponse<AuthResponse.LoginResponseDto> loginUnion(
            @RequestBody AuthRequest.LoginRequestDto dto
    ) {
        return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS_UNION,
                authService.login(dto));
    }

    @Operation(summary = "로그아웃", description = "로그인 회원 로그아웃")
    @DeleteMapping("/logout/{userId}")
    public ApiResponse<Void> logout(
            @PathVariable(name = "userId") Long userId
    ) {
        authService.logout(userId);
        return ApiResponse.of(SuccessStatus.LOGOUT_SUCCESS, null);
    }

    @Operation(summary = "Reissue Token", description = "Access Token 재발급")
    @GetMapping("/reissue")
    public ApiResponse<AuthResponse.ReissueResponseDto> reissueToken(
            @RequestHeader(AUTHORIZATION) String refreshToken
    ) {
        return ApiResponse.of(SuccessStatus.TOKEN_REISSUE_COMPLETE,
                authService.reissueToken(refreshToken));
    }

    @Operation(summary = "아이디 중복확인", description = "입력받은 아이디의 중복 여부를 확인합니다.")
    @PostMapping("/check-duplicate-id")
    public ApiResponse<Void> duplicateCheckLoginId(
            @RequestBody AuthRequest.DuplicationCheckDto dto
    ) {
        authService.duplicateCheckLoginId(dto);
        return ApiResponse.of(SuccessStatus.DUPLICATION_CHECK_SUCCESS, null);
    }

    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴 시킵니다.")
    @DeleteMapping("/secession/{userId}")
    public ApiResponse<Void> withdrawMember(
            @PathVariable(name = "userId") Long userId
    ) {
        authService.withdrawMember(userId);
        return ApiResponse.of(SuccessStatus.WITHDRAW_USER_SUCCESS, null);
    }

    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경 합니다")
    @PatchMapping("/change-password")
    public ApiResponse<Void> changePassword(
            @RequestBody AuthRequest.ChangePasswordDto dto
    ) {
        authService.changePassword(dto);
        return ApiResponse.of(SuccessStatus.CHANGE_PASSWORD_SUCCESS, null);
    }


    @Operation(summary = "아이디 찾기", description = "입력받은 이메일로 로그인 아이디를 조회합니다.")
    @GetMapping("/find-id/{email}")
    public ApiResponse<AuthResponse.FindIdResponseDto> findUserId(
            @PathVariable(name = "email") String email) {
        return ApiResponse.of(SuccessStatus.FIND_LOGIN_ID_SUCCESS,
                authService.findLoginUnionId(email));
    }

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 반환 합니다.")
    @GetMapping("/users")
    public ApiResponse<AuthResponse.FindUserInfoResponseDto> findUserInfo(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.of(SuccessStatus.FIND_USER_INFO_SUCCESS,
                authService.findUserInfo(userDetails.getUsername()));
    }
}