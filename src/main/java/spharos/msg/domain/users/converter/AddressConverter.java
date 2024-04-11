package spharos.msg.domain.users.converter;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.users.dto.request.AddressRequest.AddAddressDto;
import spharos.msg.domain.users.dto.response.AddressResponse;
import spharos.msg.domain.users.dto.response.AddressResponse.SearchAddressDto;
import spharos.msg.domain.users.entity.Address;
import spharos.msg.domain.users.entity.Users;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressConverter {

    public static Address toEntity(AddAddressDto dto, Users users){
        return Address
                .builder()
                .phoneNumber(dto.getMobileNumber())
                .addressNickname(dto.getAddressName())
                .recipientPhoneNumber(dto.getAddressPhoneNumber())
                .recipient(dto.getRecipient())
                .users(users)
                .addressDetail(dto.getAddress())
                .build();
    }

    public static List<SearchAddressDto> toDto(List<Address> addressList){
        return addressList.stream()
                .map(address -> AddressResponse.SearchAddressDto.builder()
                        .addressName(address.getAddressNickname())
                        .recipient(address.getRecipient())
                        .mobileNumber(address.getPhoneNumber())
                        .addressPhoneNumber(address.getRecipientPhoneNumber())
                        .address(address.getAddressDetail())
                        .addressId(address.getId())
                        .build())
                .collect(Collectors.toList());
    }
}
