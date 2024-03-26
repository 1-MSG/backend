package spharos.msg.domain.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoLoginRequestDto {

    public String username;
    public String email;
}
