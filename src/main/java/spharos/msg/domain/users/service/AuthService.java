package spharos.msg.domain.users.service;

import spharos.msg.domain.users.dto.request.ChangePasswordRequestDto;
import spharos.msg.domain.users.dto.request.DuplicationCheckRequestDto;
import spharos.msg.domain.users.dto.request.LoginRequestDto;
import spharos.msg.domain.users.dto.response.FindIdOutDto;
import spharos.msg.domain.users.dto.response.ReissueOutDto;
import spharos.msg.domain.users.dto.request.SignUpRequestDto;
import spharos.msg.domain.users.dto.response.LoginOutDto;

public interface AuthService {

    void signUp(SignUpRequestDto signUpRequestDto);

    LoginOutDto login(LoginRequestDto loginRequestDto);

    void logout(Long userId);

    ReissueOutDto reissueToken(String token);

    void duplicateCheckLoginId(DuplicationCheckRequestDto duplicationCheckRequestDto);

    void withdrawMember(Long userId);

    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    FindIdOutDto findLoginUnionId(String email);
}
