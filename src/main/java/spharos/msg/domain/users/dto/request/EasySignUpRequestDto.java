package spharos.msg.domain.users.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EasySignUpRequestDto {

    private String email;
    private String oauthName;
    private String oauthId;
}
