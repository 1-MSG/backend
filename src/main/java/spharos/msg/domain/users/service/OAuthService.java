package spharos.msg.domain.users.service;

import java.util.Optional;
import spharos.msg.domain.users.dto.request.EasyLoginRequestDto;
import spharos.msg.domain.users.dto.request.EasySignUpRequestDto;
import spharos.msg.domain.users.dto.response.LoginOutDto;

public interface OAuthService {

    Optional<LoginOutDto> easySignUp(EasySignUpRequestDto easySignUpRequestDto);

    LoginOutDto easyLogin(EasyLoginRequestDto easyLoginRequestDto);
}
