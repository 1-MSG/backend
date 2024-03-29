package spharos.msg.domain.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginOutDto {

    private String refreshToken;
    private String accessToken;
    private String name;
    private String email;
    private String uuid;
}
