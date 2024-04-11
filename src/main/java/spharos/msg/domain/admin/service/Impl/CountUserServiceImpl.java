package spharos.msg.domain.admin.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spharos.msg.domain.admin.converter.AdminConverter;
import spharos.msg.domain.admin.dto.AdminResponseDto;
import spharos.msg.domain.admin.dto.AdminResponseDto.SearchAllInfo;
import spharos.msg.domain.admin.dto.AdminResponseDto.SearchInfo;
import spharos.msg.domain.admin.service.CountUserService;
import spharos.msg.domain.users.entity.LoginType;
import spharos.msg.domain.users.entity.UserStatus;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.redis.RedisService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountUserServiceImpl implements CountUserService {

    private final UsersRepository usersRepository;
    private final RedisService redisService;

    @Override
    public List<AdminResponseDto.SearchAllInfo> SearchUsersInfo(Pageable pageable) {
        Page<Users> findUsers = usersRepository.findAll(pageable);

        return findUsers.map(
                        m -> AdminConverter.toDto(
                                m,
                                redisService.isRefreshTokenExist(m.getUuid()),
                                getLoginType(m.getStatus())))
                .getContent();
    }

    private LoginType getLoginType(UserStatus status) {
        if (status.equals(UserStatus.NOT_USER)) {
            return LoginType.DELETE;
        } else if (status.equals(UserStatus.UNION)) {
            return LoginType.UNION;
        }
        return LoginType.EASY;
    }

    @Override
    public AdminResponseDto.ConnectCount countConnectUser() {
        return AdminResponseDto.ConnectCount
                .builder()
                .connectCount(redisService.countConnectUser())
                .build();
    }

    @Override
    public AdminResponseDto.UsersCount usersCount() {
        return AdminResponseDto.UsersCount
                .builder()
                .usersCount(usersRepository.count())
                .build();
    }

    @Override
    public AdminResponseDto.UsersCount todaySignupCount() {
        return AdminResponseDto.UsersCount
                .builder()
                .usersCount(usersRepository.countByCreatedAtAfter(LocalDate.now().atStartOfDay()))
                .build();
    }

    @Override
    public AdminResponseDto.SecessionCount secessionCount() {
        return AdminResponseDto.SecessionCount
                .builder()
                .usersSecessionCount(usersRepository.countByStatus(UserStatus.NOT_USER))
                .build();
    }

    @Override
    public List<List<AdminResponseDto.MonthlySignupCount>> monthSignupCount() {
        //타겟년도 계산
        LocalDateTime target = LocalDateTime
                .of(LocalDateTime.now().getYear() - 1, Month.JANUARY, 1, 0, 0);

        // 년도별 월별 가입자 수를 계산하여 Map으로 저장
        Map<Integer, Map<Month, Long>> yearMonthSignupCounts = new HashMap<>();

        // 모든 년도와 월에 대한 데이터 생성
        for (int year = target.getYear(); year <= LocalDateTime.now().getYear(); year++) {
            for (int month = 1; month <= 12; month++) {
                yearMonthSignupCounts.putIfAbsent(year,
                        new TreeMap<>(Comparator.comparingInt(Month::getValue)));
                yearMonthSignupCounts.get(year).put(Month.of(month), 0L);
            }
        }

        /// DB에서 타겟년월로 모든 데이터 한번에 긁어오기.
        List<Users> findUserData = usersRepository.findByCreatedAtGreaterThanEqual(target);

        // 가져온 데이터를 년도별, 월별로 분류하여 가입자 수를 계산
        for (Users user : findUserData) {
            int year = user.getCreatedAt().getYear();
            Month month = user.getCreatedAt().getMonth();

            // 해당 년도의 Map에서 해당 월의 가입자 수를 업데이트
            yearMonthSignupCounts.get(year)
                    .put(month, yearMonthSignupCounts.get(year).get(month) + 1);
        }

        // DTO로 변환하여 반환
        return yearMonthSignupCounts.entrySet().stream()
                .map(entry -> {
                    int year = entry.getKey();
                    Map<Month, Long> monthSignupCounts = entry.getValue();

                    return monthSignupCounts.entrySet().stream()
                            .map(innerEntry -> new AdminResponseDto.MonthlySignupCount(year,
                                    innerEntry.getKey(), innerEntry.getValue()))
                            .collect(Collectors.toList());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchAllInfo> SearchUsersInfoByUserName(String userName) {
        List<SearchInfo> user = usersRepository.findSearchInfoByUserName(userName);

        return user.stream().map(
                u ->  AdminConverter.toDto(
                        u,
                        redisService.isRefreshTokenExist(u.getUuid()),
                        getLoginType(u.getStatus()))).collect(Collectors.toList());
    }
}
