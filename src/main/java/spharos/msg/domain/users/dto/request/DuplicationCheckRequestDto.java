package spharos.msg.domain.users.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicationCheckRequestDto {

    private String loginId;

    @Builder
    public DuplicationCheckRequestDto(String loginId) {
        this.loginId = loginId;
    }
}
