package spharos.msg.domain.admin.converter;

import static spharos.msg.domain.admin.dto.AdminResponseDto.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.users.entity.LoginType;
import spharos.msg.domain.users.entity.Users;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminConverter {

    public static SearchAllInfo toDto(Users user, Boolean status,
            LoginType loginType) {
        return SearchAllInfo
                .builder()
                .userInfo(user.getEmail())
                .userId(user.getId())
                .userName(user.readUserName())
                .status(status)
                .LoginType(loginType)
                .build();
    }

    public static SearchAllInfo toDto(
            SearchInfo user, Boolean status, LoginType loginType) {
        return SearchAllInfo
                .builder()
                .userInfo(user.getEmail())
                .userId(user.getId())
                .userName(user.getUserName())
                .status(status)
                .LoginType(loginType)
                .build();
    }
}
