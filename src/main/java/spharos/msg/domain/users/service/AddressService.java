package spharos.msg.domain.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.dto.request.AddressRequestDto;
import spharos.msg.domain.users.dto.response.SearchAddressOutDto;
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
    public void createAddress(AddressRequestDto addressRequestDto, Long userId) {
        Users users = usersRepository.findById(userId).orElseThrow(
                () -> new UsersException(ErrorStatus.DELIVERY_ADDRESS_ADD_FAIL)
        );

        addressRepository.save(Address
                .builder()
                .mobileNumber(addressRequestDto.getMobileNumber())
                .addressName(addressRequestDto.getAddressName())
                .addressPhoneNumber(addressRequestDto.getAddressPhoneNumber())
                .recipient(addressRequestDto.getRecipient())
                .users(users)
                .address(addressRequestDto.getAddress())
                .build());
    }

    @Transactional(readOnly = true)
    public List<SearchAddressOutDto> searchAllAddress(Long userId) {
        List<Address> findAddress = addressRepository.findByUsersId(userId);
        if (findAddress.isEmpty()) {
            throw new UsersException(ErrorStatus.ADDRESS_NOT_FOUND);
        }

        return findAddress.stream()
                .map(this::convertToSearchAddressDto)
                .collect(Collectors.toList());
    }

    private SearchAddressOutDto convertToSearchAddressDto(Address address) {
        return SearchAddressOutDto.builder()
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