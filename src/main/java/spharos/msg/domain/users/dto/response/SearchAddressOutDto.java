package spharos.msg.domain.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchAddressOutDto {
    private Long addressId;
    private String addressName;
    private String recipient;
    private String mobileNumber;
    private String addressPhoneNumber;
    private String address;
}
