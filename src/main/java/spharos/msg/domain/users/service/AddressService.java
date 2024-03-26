package spharos.msg.domain.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.users.dto.NewAddressRequestDto;
import spharos.msg.domain.users.entity.Address;
import spharos.msg.domain.users.repository.AddressRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Address createNewAddress(Address address) {
        return addressRepository.save(address);
    }
}