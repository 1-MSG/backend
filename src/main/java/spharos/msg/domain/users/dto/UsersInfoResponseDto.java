package spharos.msg.domain.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsersInfoResponseDto {
    String username;
    @JsonProperty("coupon_count")
    String couponCount;
    @JsonProperty("order_count")
    String orderCount;

    @Builder
    public UsersInfoResponseDto(String username, String couponCount, String orderCount) {
        this.username = username;
        this.couponCount = couponCount;
        this.orderCount = orderCount;
    }
}
