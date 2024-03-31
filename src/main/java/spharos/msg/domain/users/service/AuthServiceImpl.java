package spharos.msg.domain.users.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.dto.request.DuplicationCheckRequestDto;
import spharos.msg.domain.users.dto.request.LoginRequestDto;
import spharos.msg.domain.users.dto.response.LoginOutDto;
import spharos.msg.domain.users.dto.response.ReissueOutDto;
import spharos.msg.domain.users.dto.request.SignUpRequestDto;
import spharos.msg.domain.users.entity.UserOAuthList;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UserOAuthListRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.UsersException;
import spharos.msg.global.redis.RedisService;
import spharos.msg.global.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final UserOAuthListRepository userOAuthListRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        String uuid = UUID.randomUUID().toString();
        Users user = new Users(uuid);
        user.passwordToHash(signUpRequestDto.getPassword());

        usersRepository.save(Users
                .builder()
                .email(signUpRequestDto.getEmail())
                .userName(signUpRequestDto.getUsername())
                .loginId(signUpRequestDto.getLoginId())
                .phoneNumber(signUpRequestDto.getPhoneNumber())
                .password(user.getPassword())
                .uuid(user.getUuid())
                .address(signUpRequestDto.getAddress())
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public LoginOutDto login(LoginRequestDto loginRequestDto) {
        Users findUser = usersRepository.findByLoginId(loginRequestDto.getLoginId())
                .orElseThrow(() -> new UsersException(ErrorStatus.LOG_IN_UNION_FAIL));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        findUser.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        //create token
        String accessToken = jwtTokenProvider.createAccessToken(findUser);
        String refreshToken = jwtTokenProvider.createRefreshToken(findUser);

        return LoginOutDto
                .builder()
                .uuid(findUser.getUuid())
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .name(findUser.readUserName())
                .email(findUser.getEmail())
                .build();
    }

    public void logout(String uuid) {
        if (Boolean.TRUE.equals(redisService.isRefreshTokenExist(uuid))) {
            redisService.deleteRefreshToken(uuid);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ReissueOutDto reissueToken(String uuid) {
        Users findUser = usersRepository.findByUuid(uuid).orElseThrow(
                () -> new UsersException(ErrorStatus.REISSUE_TOKEN_FAIL));
        String accessToken = jwtTokenProvider.createAccessToken(findUser);
        String refreshToken = jwtTokenProvider.createRefreshToken(findUser);
        return ReissueOutDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public void duplicateCheckLoginId(DuplicationCheckRequestDto duplicationCheckRequestDto) {
        if (usersRepository.existsByLoginId(duplicationCheckRequestDto.getLoginId())) {
            throw new UsersException(ErrorStatus.DUPLICATION_LOGIN_ID);
        }
    }

    @Transactional
    @Override
    public void withdrawMember(String uuid) {
        usersRepository.deleteByUuid(uuid);
        userOAuthListRepository.deleteByUuid(uuid);
    }
}
