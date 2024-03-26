package spharos.msg.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.service.UsersService;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewAddressRequestDto {

    private String addressName;
    private String recipient;
    private String mobileNumber;
    private String addressPhoneNumber;
    private String address;
    private Users users;

    public static NewAddressRequestDto signUpDtoToDto(
            SignUpRequestDto signUpRequestDto, Users users) {
        return NewAddressRequestDto
                .builder()
                .addressName(UsersService.BASIC_ADDRESS_NAME)
                .recipient(signUpRequestDto.getUsername())
                .mobileNumber(signUpRequestDto.getPhoneNumber())
                .addressPhoneNumber(signUpRequestDto.getPhoneNumber())
                .address(signUpRequestDto.getAddress())
                .users(users)
                .build();
    }
}
