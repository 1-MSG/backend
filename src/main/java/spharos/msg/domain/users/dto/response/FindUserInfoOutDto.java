package spharos.msg.domain.users.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindUserInfoOutDto {
    String userName;
    String phoneNumber;
    String email;

    @Builder
    public FindUserInfoOutDto(String userName, String phoneNumber, String email) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
