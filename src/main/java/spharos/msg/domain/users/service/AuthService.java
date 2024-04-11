package spharos.msg.domain.users.service;

import spharos.msg.domain.users.dto.request.AuthRequest;
import spharos.msg.domain.users.dto.response.AuthResponse;

public interface AuthService {

    void signUp(AuthRequest.SignUpRequestDto dto);

    AuthResponse.LoginResponseDto login(AuthRequest.LoginRequestDto dto);

    void logout(Long userId);

    AuthResponse.ReissueResponseDto reissueToken(String token);

    void duplicateCheckLoginId(AuthRequest.DuplicationCheckDto dto);

    void withdrawMember(Long userId);

    void changePassword(AuthRequest.ChangePasswordDto dto);

    AuthResponse.FindIdResponseDto findLoginUnionId(String email);

    AuthResponse.FindUserInfoResponseDto findUserInfo(String uuid);
}
