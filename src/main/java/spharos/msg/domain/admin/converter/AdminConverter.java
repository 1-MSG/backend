package spharos.msg.domain.admin.converter;

import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JANUARY;
import static spharos.msg.domain.admin.dto.AdminResponseDto.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
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

    // 년/월별 데이터 원하는 데이터로 가공
    public static List<List<MonthlySignupCount>> toDto(List<MonthlySignupCountV2> countData,
            LocalDateTime StartDate) {

        List<List<MonthlySignupCount>> result = new ArrayList<>();

        for (int year = StartDate.getYear(); year <= StartDate.getYear() + 1; year++) {
            List<MonthlySignupCount> monthlyCounts = new ArrayList<>();
            for (int month = JANUARY + 1; month <= DECEMBER + 1; month++) {
                MonthlySignupCount count = null;
                for (MonthlySignupCountV2 mc : countData) {
                    if (mc.getYear() == year && mc.getMonth() == month) {
                        count = new MonthlySignupCount(mc.getYear(), Month.of(mc.getMonth()),
                                mc.getCount());
                        break;
                    }
                }
                if (count == null) {
                    count = new MonthlySignupCount(year, Month.of(month), 0L);
                }
                monthlyCounts.add(count);
            }
            result.add(monthlyCounts);
        }
        return result;
    }
}
