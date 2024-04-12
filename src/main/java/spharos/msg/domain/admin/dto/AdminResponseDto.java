package spharos.msg.domain.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.Month;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spharos.msg.domain.users.entity.LoginType;
import spharos.msg.domain.users.entity.UserStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MonthlySignupCount {

        private int year;
        private Month month;
        private Long count;
    }

    @Builder
    @Getter
    public static class MonthlySignupCountV2 {

        private int year;
        private int month;
        private Long count;

        @QueryProjection
        public MonthlySignupCountV2(int year, int month, Long count) {
            this.year = year;
            this.month = month;
            this.count = count;
        }
    }


    @Builder
    @AllArgsConstructor
    @Getter
    @ToString
    public static class CountPriceDto {

        private Long totalDeliveryPrice;
        private Long totalProfit;
        private Long totalOrderPrice;
    }

    @Builder
    @Getter
    public static class SearchInfo {

        private Long Id;
        private String userName;
        private String email;
        private UserStatus status;
        private String uuid;

        @QueryProjection
        public SearchInfo(Long id, String userName, String email, UserStatus status, String uuid) {
            this.Id = id;
            this.userName = userName;
            this.email = email;
            this.status = status;
            this.uuid = uuid;
        }
    }
}
