package spharos.msg.domain.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OAuthRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginDto {
        private String oauthName;
        private String oauthId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EasySignUpDto {
        private String email;
        private String oauthName;
        private String oauthId;
    }
}
