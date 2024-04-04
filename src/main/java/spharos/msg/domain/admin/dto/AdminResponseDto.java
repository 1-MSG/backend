package spharos.msg.domain.admin.dto;

import java.time.Month;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import spharos.msg.domain.users.entity.LoginType;

public class AdminResponseDto {

    @Builder
    @AllArgsConstructor
    @Getter
    public static class SearchAllInfo {

        private Long userId;
        private String userName;
        private String userInfo;
        private LoginType LoginType;
        private Boolean status;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class ConnectCount {

        private Long connectCount;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class UsersCount {

        private Long usersCount;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class SecessionCount {

        private Long usersSecessionCount;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class MonthlySignupCount {
        private int year;
        private Month month;
        private Long count;
    }
}
