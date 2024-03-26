package spharos.msg.global.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String REDIS_KEY_PREFIX = "JwtRefreshToken:";
    //RedisTemplate 주입
    private final RedisTemplate<String, String> redisTemplate;

    // Redis에 토큰 저장
    public void saveRefreshToken(String uuid, String token, long expirationTimeMs) {
        String key = REDIS_KEY_PREFIX + uuid;
        redisTemplate.opsForValue().set(key, token, expirationTimeMs, TimeUnit.MILLISECONDS);
    }

    // Redis에서 토큰 조회
    public String getRefreshToken(String uuid) {
        String key = REDIS_KEY_PREFIX + uuid;
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean isRefreshTokenExist(String uuid) {
        String key = REDIS_KEY_PREFIX + uuid;
        Optional<String> value = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        return value.isPresent() && !value.get().isEmpty();
    }

    // Redis에서 토큰 삭제
    public void deleteRefreshToken(String uuid) {
        String key = REDIS_KEY_PREFIX + uuid;
        redisTemplate.delete(key);
    }
}