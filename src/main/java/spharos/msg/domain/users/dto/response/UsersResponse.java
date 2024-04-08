package spharos.msg.domain.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UsersResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailResponseDto {
        String secretKey;
    }
}
