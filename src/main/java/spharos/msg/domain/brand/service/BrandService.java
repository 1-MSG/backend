package spharos.msg.domain.brand.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.brand.dto.BrandResponseDto;
import spharos.msg.domain.brand.repository.BrandRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    public ApiResponse<?> getBrands() {
        return ApiResponse.of(SuccessStatus.BRAND_GET_SUCCESS,
                 brandRepository.findAll()
                        .stream()
                        .map(BrandResponseDto::new)
                        .collect(Collectors.toSet()));
    }
}
