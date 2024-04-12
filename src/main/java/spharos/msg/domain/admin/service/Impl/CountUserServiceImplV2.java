package spharos.msg.domain.admin.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spharos.msg.domain.admin.converter.AdminConverter;
import spharos.msg.domain.admin.dto.AdminResponseDto;
import spharos.msg.domain.admin.service.CountUserService;
import spharos.msg.domain.users.entity.LoginType;
import spharos.msg.domain.users.entity.UserStatus;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.redis.RedisService;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class CountUserServiceImplV2 implements CountUserService {

    private final UsersRepository usersRepository;
    private final RedisService redisService;

    @Override
    public List<AdminResponseDto.SearchAllInfo> SearchUsersInfo(Pageable pageable) {
        List<AdminResponseDto.SearchInfo> findUsersDto = usersRepository.findUsersByPageable(
                pageable);

        return findUsersDto.stream()
                .map(users -> AdminConverter.toDto(users,
                        redisService.isRefreshTokenExist(users.getUuid()),
                        getLoginType(users.getStatus()))).collect(Collectors.toList());
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

        LocalDateTime startDate = LocalDateTime
                .of(LocalDateTime.now().getYear() - 1, Month.JANUARY, 1, 0, 0);

        return AdminConverter.toDto(usersRepository.CountByMonthly(startDate), startDate);
    }

    @Override
    public List<AdminResponseDto.SearchAllInfo> SearchUsersInfoByUserName(String userName) {
        List<AdminResponseDto.SearchInfo> user = usersRepository.findSearchInfoByUserName(userName);

        return user.stream().map(
                u -> AdminConverter.toDto(
                        u,
                        redisService.isRefreshTokenExist(u.getUuid()),
                        getLoginType(u.getStatus()))).collect(Collectors.toList());
    }
}
