package spharos.msg.domain.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    public String loginId;
    public String password;
}
