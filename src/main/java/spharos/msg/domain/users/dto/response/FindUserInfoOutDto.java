package spharos.msg.domain.users.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindUserInfoOutDto {
    String userName;

    @Builder
    public FindUserInfoOutDto(String userName) {
        this.userName = userName;
    }
}
