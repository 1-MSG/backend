package spharos.msg.domain.users.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EasyLoginRequestDto {

    private String oauthName;
    private String oauthId;
}
