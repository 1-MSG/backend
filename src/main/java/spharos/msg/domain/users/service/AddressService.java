package spharos.msg.domain.users.service;

import java.util.List;
import spharos.msg.domain.users.dto.request.AddressRequest;
import spharos.msg.domain.users.dto.response.AddressResponse;

public interface AddressService {
    void createAddress(AddressRequest.AddAddressDto dto, Long userId);

    List<AddressResponse.SearchAddressDto> searchAllAddress(Long userId);

    void deleteAddress(Long userId, Long addressId);
}
