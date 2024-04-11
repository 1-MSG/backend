package spharos.msg.domain.users.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.users.dto.request.AuthRequest.SignUpRequestDto;
import spharos.msg.domain.users.dto.response.AuthResponse;
import spharos.msg.domain.users.entity.UserStatus;
import spharos.msg.domain.users.entity.Users;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConverter {

    public static Users toEntity(SignUpRequestDto dto, Users user){
        return Users
                .builder()
                .email(dto.getEmail())
                .userName(dto.getUsername())
                .loginId(dto.getLoginId())
                .phoneNumber(dto.getPhoneNumber())
                .password(user.getPassword())
                .uuid(user.getUuid())
                .address(dto.getAddress())
                .status(UserStatus.UNION)
                .build();
    }

    public static AuthResponse.LoginResponseDto toDto(Users user, String accessToken, String refreshToken){
        return AuthResponse.LoginResponseDto
                .builder()
                .userId(user.getId())
                .uuid(user.getUuid())
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .name(user.readUserName())
                .email(user.getEmail())
                .build();
    }

    public static AuthResponse.ReissueResponseDto toDto(String accessToken, String refreshToken){
        return AuthResponse.ReissueResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static AuthResponse.FindIdResponseDto toDto(Users user){
        return AuthResponse.FindIdResponseDto
                .builder()
                .loginId(user.getLoginId())
                .build();
    }

    public static AuthResponse.FindUserInfoResponseDto toDtoForUserInfo(Users user){
        return AuthResponse.FindUserInfoResponseDto
                .builder()
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .userName(user.readUserName())
                .build();
    }

    public static Users toEntity(Users user, UserStatus userStatus){
        return Users
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.readUserName())
                .loginId(user.getLoginId())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .uuid(user.getUuid())
                .address(user.getAddress())
                .status(userStatus)
                .build();
    }

    public static Users toEntity(Users user, String newPassword) {
        return Users
                .builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .uuid(user.getUuid())
                .password(newPassword)
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .userName(user.readUserName())
                .address(user.getAddress())
                .status(user.getStatus())
                .build();
    }
}
