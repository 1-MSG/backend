package spharos.msg.domain.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.converter.AuthConverter;
import spharos.msg.domain.users.dto.request.AuthRequest;
import spharos.msg.domain.users.dto.response.AuthResponse;
import spharos.msg.domain.users.entity.UserStatus;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.domain.users.service.AuthService;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.JwtTokenException;
import spharos.msg.global.api.exception.UsersException;
import spharos.msg.global.redis.RedisService;
import spharos.msg.global.security.JwtTokenProvider;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class AuthServiceImplV2 implements AuthService {
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    @Override
    public void signUp(AuthRequest.SignUpRequestDto dto) {
        Optional<UserStatus> statusByLoginId = usersRepository.findStatusByLoginId(dto.getLoginId());

        if(statusByLoginId.isPresent()){
            if(statusByLoginId.get() == UserStatus.NOT_USER){
                throw new UsersException(ErrorStatus.WITHDRAW_USER_FAIL);
            }
            throw new UsersException(ErrorStatus.DUPLICATION_LOGIN_ID);
        }

        String uuid = UUID.randomUUID().toString();
        Users user = new Users(uuid);
        user.passwordToHash(dto.getPassword());

        usersRepository.save(
                AuthConverter.toEntity(dto, user));
    }

    @Transactional(readOnly = true)
    @Override
    public AuthResponse.LoginResponseDto login(AuthRequest.LoginRequestDto dto) {
        Users findUser = usersRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(() -> new UsersException(ErrorStatus.LOG_IN_UNION_FAIL));

        //탈퇴 회원 검증 로직 추가
        if (findUser.getStatus().equals(UserStatus.NOT_USER)) {
            throw new UsersException(ErrorStatus.WITHDRAW_USER_FAIL);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        findUser.getUsername(),
                        dto.getPassword()
                )
        );

        //create token
        String accessToken = jwtTokenProvider.createAccessToken(findUser);
        String refreshToken = jwtTokenProvider.createRefreshToken(findUser);

        return AuthConverter.toDto(findUser, accessToken, refreshToken);
    }

    public void logout(Long userId) {
        Users findUser = usersRepository.findById(userId).orElseThrow();
        if (Boolean.TRUE.equals(redisService.isRefreshTokenExist(findUser.getUuid()))) {
            redisService.deleteRefreshToken(findUser.getUuid());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public AuthResponse.ReissueResponseDto reissueToken(String oldRefreshToken) {
        String uuid = jwtTokenProvider.getUuid(oldRefreshToken);
        String oldToken = oldRefreshToken.substring(7);

        Users findUser = usersRepository.findByUuid(uuid).orElseThrow(
                () -> new JwtTokenException(ErrorStatus.REISSUE_TOKEN_FAIL));

        if (!redisService.getRefreshToken(uuid).equals(oldToken)) {
            throw new JwtTokenException(ErrorStatus.REISSUE_TOKEN_FAIL);
        }

        String accessToken = jwtTokenProvider.createAccessToken(findUser);
        String refreshToken = jwtTokenProvider.createRefreshToken(findUser);

        return AuthConverter.toDto(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    @Override
    public void duplicateCheckLoginId(AuthRequest.DuplicationCheckDto dto) {
        if (usersRepository.existsByLoginId(dto.getLoginId())) {
            throw new UsersException(ErrorStatus.DUPLICATION_LOGIN_ID);
        }
    }

    @Transactional
    @Override
    public void withdrawMember(Long userId) {
        Users findUser = usersRepository.findById(userId).orElseThrow(
                () -> new UsersException(ErrorStatus.NOT_USER)
        );
        usersRepository.save(AuthConverter.toEntity(findUser, UserStatus.NOT_USER));
    }

    @Transactional
    @Override
    public void changePassword(AuthRequest.ChangePasswordDto dto) {
        Users findUser = usersRepository.findByLoginId(dto.getLoginId())
                .orElseThrow();

        if (validatePassword(findUser.getPassword(),
                dto.getModifyPassword())) {
            throw new UsersException(ErrorStatus.SAME_PASSWORD);
        }

        usersRepository.save(
                AuthConverter.toEntity(findUser, hashPassword(dto.getModifyPassword())));
    }

    private Boolean validatePassword(String oldPasswordHash, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(newPassword, oldPasswordHash);
    }

    private String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }


    @Transactional(readOnly = true)
    @Override
    public AuthResponse.FindIdResponseDto findLoginUnionId(String email) {
        String findLoginId = usersRepository.findLoginIdByEmail(email).orElseThrow(
                () -> new UsersException(ErrorStatus.FIND_LOGIN_ID_FAIL));

        return AuthResponse.FindIdResponseDto.builder().loginId(findLoginId).build();
    }

    @Override
    public AuthResponse.FindUserInfoResponseDto findUserInfo(String uuid) {
        return usersRepository.findUserInfoByUuid(uuid).orElseThrow(
                () -> new UsersException(ErrorStatus.FIND_USER_INFO_FAIL));
    }
}
