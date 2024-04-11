package spharos.msg.domain.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AddressResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchAddressDto {
        private Long addressId;
        private String addressName;
        private String recipient;
        private String mobileNumber;
        private String addressPhoneNumber;
        private String address;
    }
}
