package spharos.msg.domain.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.dto.request.AddressRequest;
import spharos.msg.domain.users.dto.response.AddressResponse;
import spharos.msg.domain.users.entity.Address;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.AddressRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.UsersException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public void createAddress(AddressRequest.AddAddressDto dto, Long userId) {
        Users users = usersRepository.findById(userId).orElseThrow(
                () -> new UsersException(ErrorStatus.DELIVERY_ADDRESS_ADD_FAIL)
        );

        addressRepository.save(Address
                .builder()
                .phoneNumber(dto.getMobileNumber())
                .addressNickname(dto.getAddressName())
                .recipientPhoneNumber(dto.getAddressPhoneNumber())
                .recipient(dto.getRecipient())
                .users(users)
                .addressDetail(dto.getAddress())
                .build());
    }

    @Transactional(readOnly = true)
    public List<AddressResponse.SearchAddressDto> searchAllAddress(Long userId) {
        List<Address> findAddress = addressRepository.findByUsersId(userId);

        return findAddress.stream()
                .map(this::convertToSearchAddressDto)
                .collect(Collectors.toList());
    }

    private AddressResponse.SearchAddressDto convertToSearchAddressDto(Address address) {
        return AddressResponse.SearchAddressDto.builder()
                .addressName(address.getAddressNickname())
                .recipient(address.getRecipient())
                .mobileNumber(address.getPhoneNumber())
                .addressPhoneNumber(address.getRecipientPhoneNumber())
                .address(address.getAddressDetail())
                .addressId(address.getId())
                .build();
    }

    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        //주소 있는지 검증 필요?
        Address findAddress = addressRepository.findByUsersIdAndId(userId, addressId).orElseThrow();
        addressRepository.delete(findAddress);
    }
}