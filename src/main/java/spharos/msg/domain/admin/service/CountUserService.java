package spharos.msg.domain.admin.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spharos.msg.domain.admin.dto.SearchUsersInfoRequestDto;
import spharos.msg.domain.admin.repository.CountUserRepository;
import spharos.msg.domain.users.entity.LoginType;
import spharos.msg.domain.users.entity.UserOAuthList;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.UserOAuthListRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.redis.RedisService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountUserService {

    private final UsersRepository usersRepository;
    private final CountUserRepository countUserRepository;
    private final UserOAuthListRepository userOAuthListRepository;
    private final RedisService redisService;

    public List<SearchUsersInfoRequestDto>SearchUsersInfo(Pageable pageable){
        Page<Users> findUsers = usersRepository.findAll(pageable);

        return findUsers.map(m -> SearchUsersInfoRequestDto
                .builder()
                .userInfo(m.getEmail())
                .userId(m.getId())
                .userName(m.readUserName())
                .status(redisService.isRefreshTokenExist(m.getUuid()))
                .LoginType(getLoginType(m.getUuid()))
                .build()).getContent();
    }

    private LoginType getLoginType(String uuid){
        List<UserOAuthList> userOAuthLists = userOAuthListRepository.findByUuid(uuid);
        if(userOAuthLists.isEmpty()){
            return LoginType.UNION;
        }
        return LoginType.EASY;
    }
}
