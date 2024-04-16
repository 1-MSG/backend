package spharos.msg.domain.users.service.impl.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.converter.AuthConverter;
import spharos.msg.domain.users.converter.OAuthConverter;
import spharos.msg.domain.users.dto.request.OAuthRequest;
import spharos.msg.domain.users.dto.response.AuthResponse;
import spharos.msg.domain.users.dto.response.OAuthResponse;
import spharos.msg.domain.users.entity.UserOAuthList;
import spharos.msg.domain.users.entity.UserStatus;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UserOAuthListRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.domain.users.service.OAuthService;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.UsersException;
import spharos.msg.global.security.JwtTokenProvider;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class OAuthServiceImplV2 implements OAuthService {

    private final UsersRepository userRepository;
    private final UserOAuthListRepository userOAuthListRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public Optional<OAuthResponse.EasyLoginResponseDto> easySignUp(OAuthRequest.EasySignUpRequestDto dto) {
        Users findUser = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new UsersException(ErrorStatus.NOT_UNION_USER)
        );

        if (findUser.getStatus().equals(UserStatus.NOT_USER)) {
            throw new UsersException(ErrorStatus.WITHDRAW_USER_FAIL);
        }

        if (Boolean.TRUE.equals(userOAuthListRepository.existsByUuid(findUser.getUuid())) &&
                findUser.getStatus().equals(UserStatus.EASY)) {
            log.info("기존 가입된 회원이라 바로 로그인 처리");
            return Optional.of(easyLogin(OAuthRequest.EasyLoginRequestDto
                    .builder()
                    .oauthId(dto.getOauthId())
                    .oauthName(dto.getOauthName())
                    .build()));
        }

        userOAuthListRepository.save(OAuthConverter.toEntity(dto, findUser.getUuid()));

        userRepository.save(AuthConverter.toEntity(findUser, UserStatus.EASY));

        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public OAuthResponse.EasyLoginResponseDto easyLogin(OAuthRequest.EasyLoginRequestDto dto) {
        UserOAuthList userOAuthList = userOAuthListRepository.findByOAuthIdAndOAuthName(
                dto.getOauthId(), dto.getOauthName()).orElseThrow(
                () -> new UsersException(ErrorStatus.LOG_IN_EASY_FAIL));

        Users findUser = userRepository.findByUuid(userOAuthList.getUuid()).orElseThrow(
                () -> new UsersException(ErrorStatus.NOT_UNION_USER));

        if (findUser.getStatus().equals(UserStatus.NOT_USER)) {
            throw new UsersException(ErrorStatus.WITHDRAW_USER_FAIL);
        }

        String refreshToken = jwtTokenProvider.createRefreshToken(findUser);
        String accessToken = jwtTokenProvider.createAccessToken(findUser);

        return OAuthConverter.toDtoEasy(findUser, refreshToken, accessToken);
    }

    @Transactional(readOnly = true)
    @Override
    public OAuthResponse.FindEasyIdResponseDto findLoginEasyId(String email) {
        AuthResponse.UserUuidAndLoginId findData = userRepository.findUuidAndLoginIdByEmail(email).orElseThrow(
                () -> new UsersException(ErrorStatus.FIND_LOGIN_ID_FAIL));

        if(!userOAuthListRepository.existsByUuid(findData.getUuid())){
            throw new UsersException(ErrorStatus.FIND_LOGIN_ID_FAIL);
        }

        return OAuthResponse.FindEasyIdResponseDto
                .builder().loginId(findData.getLoginId()).build();
    }
}
