package spharos.msg.domain.admin.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.admin.dto.AdminResponseDto;
import spharos.msg.domain.users.entity.LoginType;
import spharos.msg.domain.users.entity.Users;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminConverter {

    public static AdminResponseDto.SearchAllInfo toDto(Users user, Boolean status, LoginType loginType){
        return AdminResponseDto.SearchAllInfo
                .builder()
                .userInfo(user.getEmail())
                .userId(user.getId())
                .userName(user.readUserName())
                .status(status)
                .LoginType(loginType)
                .build();
    }
}
