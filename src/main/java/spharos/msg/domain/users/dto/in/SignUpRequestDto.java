package spharos.msg.domain.users.dto.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    public String loginId;
    public String username;
    public String password;
    public String address;
    public String phoneNumber;
    public String email;
    public Boolean isEasy;
}
