package spharos.msg.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoSignUpRequestDto {

    private String userUuid;

    public static KakaoSignUpRequestDto uuidToDto(String uuid) {
        return KakaoSignUpRequestDto
                .builder()
                .userUuid(uuid)
                .build();
    }
}
