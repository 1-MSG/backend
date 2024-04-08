package spharos.msg.domain.users.service;

import spharos.msg.domain.users.dto.request.AuthRequest;
import spharos.msg.domain.users.dto.response.FindIdOutDto;
import spharos.msg.domain.users.dto.response.FindUserInfoOutDto;
import spharos.msg.domain.users.dto.response.ReissueOutDto;
import spharos.msg.domain.users.dto.response.LoginOutDto;

public interface AuthService {

    void signUp(AuthRequest.SignUpDto dto);

    LoginOutDto login(AuthRequest.LoginDto dto);

    void logout(Long userId);

    ReissueOutDto reissueToken(String token);

    void duplicateCheckLoginId(AuthRequest.DuplicationCheckDto dto);

    void withdrawMember(Long userId);

    void changePassword(AuthRequest.ChangePasswordDto dto);

    FindIdOutDto findLoginUnionId(String email);

    FindUserInfoOutDto findUserInfo(String uuid);
}
