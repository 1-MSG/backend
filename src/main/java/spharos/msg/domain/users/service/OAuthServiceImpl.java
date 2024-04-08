package spharos.msg.domain.users.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.dto.request.OAuthRequest;
import spharos.msg.domain.users.dto.response.OAuthResponse;
import spharos.msg.domain.users.entity.UserOAuthList;
import spharos.msg.domain.users.entity.UserStatus;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UserOAuthListRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.UsersException;
import spharos.msg.global.security.JwtTokenProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

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

        //업데이트 처리
        userOAuthListRepository.save(UserOAuthList
                .builder()
                .OAuthId(dto.getOauthId())
                .OAuthName(dto.getOauthName())
                .uuid(findUser.getUuid())
                .build());

        userRepository.save(Users
                .builder()
                .id(findUser.getId())
                .email(findUser.getEmail())
                .userName(findUser.readUserName())
                .loginId(findUser.getLoginId())
                .phoneNumber(findUser.getPhoneNumber())
                .password(findUser.getPassword())
                .uuid(findUser.getUuid())
                .address(findUser.getAddress())
                .status(UserStatus.EASY)
                .build()
        );

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

        //탈퇴 회원 검증 로직 추가
        if (findUser.getStatus().equals(UserStatus.NOT_USER)) {
            throw new UsersException(ErrorStatus.WITHDRAW_USER_FAIL);
        }

        //todo : 인증처리 생각 할 것.
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        findUser.getUsername(),
//                        loginRequestDto.getPassword()
//                )
//        );

        //토큰생성
        String refreshToken = jwtTokenProvider.createRefreshToken(findUser);
        String accessToken = jwtTokenProvider.createAccessToken(findUser);

        return OAuthResponse.EasyLoginResponseDto
                .builder()
                .uuid(findUser.getUuid())
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .name(findUser.readUserName())
                .email(findUser.getEmail())
                .userId(findUser.getId())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public OAuthResponse.FindEasyIdResponseDto findLoginEasyId(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsersException(ErrorStatus.FIND_LOGIN_ID_FAIL));

        List<UserOAuthList> userOAuthList = userOAuthListRepository.findByUuid(user.getUuid());
        if (userOAuthList.isEmpty()) {
            throw new UsersException(ErrorStatus.FIND_LOGIN_ID_FAIL);
        }

        return OAuthResponse.FindEasyIdResponseDto
                .builder()
                .loginId(user.getLoginId())
                .build();
    }
}
