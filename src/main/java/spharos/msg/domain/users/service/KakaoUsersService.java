package spharos.msg.domain.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.entity.KakaoUsers;
import spharos.msg.domain.users.repository.KakaoUsersRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoUsersService {

    private final KakaoUsersRepository kakaoUsersRepository;

    @Transactional
    public void createKakaoUsers(String uuid) {
        kakaoUsersRepository.save(KakaoUsers.builder().userUuid(uuid).build());
    }
}
