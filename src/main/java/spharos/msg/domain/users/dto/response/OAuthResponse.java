package spharos.msg.domain.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OAuthResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EasyLoginResponseDto {
        private String refreshToken;
        private String accessToken;
        private Long userId;
        private String name;
        private String email;
        private String uuid;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindEasyIdResponseDto {
        private String loginId;
    }
}
