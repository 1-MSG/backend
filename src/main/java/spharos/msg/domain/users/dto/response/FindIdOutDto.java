package spharos.msg.domain.users.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindIdOutDto {
    private String loginId;

    @Builder
    public FindIdOutDto(String loginId) {
        this.loginId = loginId;
    }
}
