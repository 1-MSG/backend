package spharos.msg.domain.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import spharos.msg.domain.users.converter.AddressConverter;
import spharos.msg.domain.users.dto.request.AddressRequest;
import spharos.msg.domain.users.dto.response.AddressResponse;
import spharos.msg.domain.users.entity.Address;
import spharos.msg.domain.users.entity.Users;
import spharos.msg.domain.users.repository.AddressRepository;
import spharos.msg.domain.users.repository.UsersRepository;
import spharos.msg.domain.users.service.AddressService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class AddressServiceImplV2 implements AddressService {

    private final AddressRepository addressRepository;
    private final UsersRepository usersRepository;

    @Override
    public void createAddress(AddressRequest.AddAddressDto dto, Long userId) {
        Users findUser = usersRepository.getReferenceById(userId);

        addressRepository.save(AddressConverter.toEntity(dto, findUser));
    }

    @Override
    public List<AddressResponse.SearchAddressDto> searchAllAddress(Long userId) {
        List<Address> findAddress = addressRepository.findByUsersId(userId);

        return AddressConverter.toDto(findAddress);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        Address findAddress = addressRepository.findByUsersIdAndId(userId, addressId).orElseThrow();
        addressRepository.delete(findAddress);
    }
}
