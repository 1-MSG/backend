package spharos.msg.domain.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.converter.AddressConverter;
import spharos.msg.domain.users.dto.request.AddressRequest;
import spharos.msg.domain.users.dto.response.AddressResponse;
import spharos.msg.domain.users.entity.Address;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.AddressRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.domain.users.service.AddressService;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.UsersException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UsersRepository usersRepository;

    @Transactional
    @Override
    public void createAddress(AddressRequest.AddAddressDto dto, Long userId) {
        Users findUser = usersRepository.findById(userId).orElseThrow(
                () -> new UsersException(ErrorStatus.DELIVERY_ADDRESS_ADD_FAIL)
        );

        addressRepository.save(AddressConverter.toEntity(dto, findUser));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AddressResponse.SearchAddressDto> searchAllAddress(Long userId) {
        List<Address> findAddress = addressRepository.findByUsersId(userId);

        return AddressConverter.toDto(findAddress);
    }

    @Transactional
    @Override
    public void deleteAddress(Long userId, Long addressId) {
        Address findAddress = addressRepository.findByUsersIdAndId(userId, addressId).orElseThrow();
        addressRepository.delete(findAddress);
    }
}