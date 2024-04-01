package spharos.msg.domain.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spharos.msg.domain.users.entity.LoginType;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchUsersInfoRequestDto {
    private Long userId;
    private String userName;
    private String userInfo;
    private spharos.msg.domain.users.entity.LoginType LoginType;
    private Boolean status;
}
