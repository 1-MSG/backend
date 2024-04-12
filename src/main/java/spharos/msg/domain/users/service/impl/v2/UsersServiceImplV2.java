package spharos.msg.domain.users.service.impl.v2;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.dto.request.UsersRequest;
import spharos.msg.domain.users.dto.response.UsersResponse;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.domain.users.service.UsersService;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.UsersException;
import spharos.msg.global.redis.RedisService;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImplV2 implements UsersService {

    private final JavaMailSender mailSender;
    private final RedisService redisService;
    private final UsersRepository userRepository;

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.expiration}")
    private long expiration;
    @Value("${spring.mail.stringSize}")
    private int stringSize;

    @Override
    public UsersResponse.EmailResponseDto sendMail(UsersRequest.EmailSendDto dto) {
        MimeMessage message = mailSender.createMimeMessage();
        String secretKey = createKey();
        try {
            String email = dto.getEmail();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("MSG.COM 인증 메일 입니다");
            mimeMessageHelper.setText("인증번호 : " + secretKey);
            mailSender.send(message);

            if (redisService.isEmailSecretKeyExist(email)) {
                redisService.deleteEmailSecretKey(email);
            }
            redisService.saveEmailSecretKey(email, secretKey, expiration);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return UsersResponse.EmailResponseDto.builder().secretKey(secretKey).build();
    }

    private String createKey() {
        int leftLimit = '0';
        int rightLimit = 'z';
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= '9' || i >= 'A') && (i <= 'Z' || i >= 'a'))
                .limit(stringSize)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public void authenticateEmail(UsersRequest.EmailAuthenticationDto dto) {
        String findSecretKey = redisService.getEmailSecretKey(dto.getEmail());
        if (!Objects.equals(findSecretKey, dto.getSecretKey())) {
            throw new UsersException(ErrorStatus.EMAIL_VALIDATE_FAIL);
        }
        redisService.deleteEmailSecretKey(dto.getEmail());
    }

    @Override
    //Email 중복 확인
    @Transactional(readOnly = true)
    public void duplicateCheckEmail(UsersRequest.EmailSendDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UsersException(ErrorStatus.ALREADY_EXIST_EMAIL);
        }
    }
}
