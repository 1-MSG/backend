package spharos.msg.domain.users.service;

import java.util.Optional;
import spharos.msg.domain.users.dto.request.OAuthRequest;
import spharos.msg.domain.users.dto.response.OAuthResponse;

public interface OAuthService {

    Optional<OAuthResponse.EasyLoginResponseDto> easySignUp(OAuthRequest.EasySignUpRequestDto dto);

    OAuthResponse.EasyLoginResponseDto easyLogin(OAuthRequest.EasyLoginRequestDto dto);

    OAuthResponse.FindEasyIdResponseDto findLoginEasyId(String email);
}
