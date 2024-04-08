package spharos.msg.domain.users.service;

import java.util.Optional;
import spharos.msg.domain.users.dto.request.OAuthRequest;
import spharos.msg.domain.users.dto.response.FindIdOutDto;
import spharos.msg.domain.users.dto.response.LoginOutDto;

public interface OAuthService {

    Optional<LoginOutDto> easySignUp(OAuthRequest.EasySignUpDto dto);

    LoginOutDto easyLogin(OAuthRequest.LoginDto dto);

    FindIdOutDto findLoginEasyId(String email);
}
