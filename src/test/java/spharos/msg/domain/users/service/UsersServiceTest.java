package spharos.msg.domain.users.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spharos.msg.domain.users.dto.request.EmailSendRequestDto;
import spharos.msg.domain.users.dto.response.EmailOutDto;
import spharos.msg.global.redis.RedisService;

@SpringBootTest
class UsersServiceTest {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RedisService redisService;

    @Test
    void 메일_송부_테스트(){
        String testEmail = "test@test.com";
        EmailSendRequestDto emailSendRequestDto = new EmailSendRequestDto();
        emailSendRequestDto.setEmail(testEmail);
        EmailOutDto emailOutDto = usersService.sendMail(emailSendRequestDto);

        String savedSecretKey = redisService.getEmailSecretKey(testEmail);
        Assertions.assertThat(emailOutDto.getSecretKey()).isEqualTo(savedSecretKey);
    }
}