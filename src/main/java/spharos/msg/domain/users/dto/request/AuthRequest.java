package spharos.msg.domain.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangePasswordDto {
        private String loginId;
        private String modifyPassword;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DuplicationCheckDto {
        private String loginId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpDto {
        public String loginId;
        public String username;
        public String password;
        public String address;
        public String phoneNumber;
        public String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginDto {
        public String loginId;
        public String password;
    }
}
