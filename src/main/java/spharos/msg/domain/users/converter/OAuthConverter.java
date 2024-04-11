package spharos.msg.domain.users.converter;

import spharos.msg.domain.users.dto.request.OAuthRequest;
import spharos.msg.domain.users.dto.response.OAuthResponse;
import spharos.msg.domain.users.entity.UserOAuthList;
import spharos.msg.domain.users.entity.Users;

public class OAuthConverter {

    public static UserOAuthList toEntity(OAuthRequest.EasySignUpRequestDto dto, String uuid) {
        return UserOAuthList
                .builder()
                .OAuthId(dto.getOauthId())
                .OAuthName(dto.getOauthName())
                .uuid(uuid)
                .build();
    }

    public static OAuthResponse.EasyLoginResponseDto toDtoEasy(Users user, String accessToken, String refreshToken){
        return OAuthResponse.EasyLoginResponseDto
                .builder()
                .uuid(user.getUuid())
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .name(user.readUserName())
                .email(user.getEmail())
                .userId(user.getId())
                .build();
    }

    public static OAuthResponse.FindEasyIdResponseDto toDtoEasy(Users user){
        return OAuthResponse.FindEasyIdResponseDto
                .builder()
                .loginId(user.getLoginId())
                .build();
    }
}
