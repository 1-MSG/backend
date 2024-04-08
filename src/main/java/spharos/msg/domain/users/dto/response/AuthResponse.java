package spharos.msg.domain.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponseDto {
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
    public static class FindUserInfoResponseDto {
        private String userName;
        private String phoneNumber;
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindIdResponseDto {
        private String loginId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReissueResponseDto {
        private String refreshToken;
        private String accessToken;
    }
}
