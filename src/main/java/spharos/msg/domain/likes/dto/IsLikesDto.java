package spharos.msg.domain.likes.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IsLikesDto {
    private boolean isLike;
    public IsLikesDto(boolean isLike) {
        this.isLike = isLike;
    }
}
