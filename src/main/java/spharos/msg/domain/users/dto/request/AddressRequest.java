package spharos.msg.domain.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AddressRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddAddressDto {
        private String addressName;
        private String recipient;
        private String mobileNumber;
        private String addressPhoneNumber;
        private String address;
    }
}
