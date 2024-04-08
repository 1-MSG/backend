package spharos.msg.domain.users.service;

import spharos.msg.domain.users.dto.request.UsersRequest;
import spharos.msg.domain.users.dto.response.UsersResponse;

public interface UsersService {

    UsersResponse.EmailResponseDto sendMail(UsersRequest.EmailSendDto dto);

    void authenticateEmail(UsersRequest.EmailAuthenticationDto dto);

    void duplicateCheckEmail(UsersRequest.EmailSendDto dto);
}
