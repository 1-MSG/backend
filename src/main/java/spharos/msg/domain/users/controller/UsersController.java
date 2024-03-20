package spharos.msg.domain.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.users.dto.LoginRequestDto;
import spharos.msg.domain.users.dto.SignUpRequestDto;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.service.UsersService;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;

    @Operation(summary = "통합회원가입", description = "통합회원가입", tags = {"User Signup"})
    @PostMapping("/signup/union")
    public ApiResponse<?> signUpUnion(@RequestBody SignUpRequestDto signUpRequestDto) {
        usersService.signUpDuplicationCheck(signUpRequestDto);
        usersService.createUsers(signUpRequestDto);
        return ApiResponse.of(SuccessStatus.SIGN_UP_SUCCESS_UNION, null);
    }

    @Operation(summary = "간편회원가입", description = "간편회원가입", tags = {"User Signup"})
    @PostMapping("/signup/easy")
    public ApiResponse<?> signUpEasy(@RequestBody SignUpRequestDto signUpRequestDto) {
        //todo : signup 구현
        return null;
    }

    @Operation(summary = "로그인", description = "통합회원 로그인", tags = {"User Login"})
    @PostMapping("login/union")
    public ApiResponse<?> loginUnion(
        @RequestBody LoginRequestDto loginRequestDto,
        HttpServletResponse response
    ) {
        Users loginUsers = usersService.login(loginRequestDto);
        usersService.createTokenAndCreateHeaders(response, loginUsers);
        return ApiResponse.of(SuccessStatus.LOGIN_SUCCESS_UNION, null);
    }

    @Operation(summary = "로그인", description = "간편회원 로그인", tags = {"User Login"})
    @PostMapping("/login/easy")
    public ApiResponse<?> loginEasy(
        @RequestBody LoginRequestDto loginRequestDto) {
        //todo : 간편 로그인 구현
        return null;
    }
}
