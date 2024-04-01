package spharos.msg.domain.brand.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.brand.dto.BrandResponseDto;
import spharos.msg.domain.brand.repository.BrandRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    public ApiResponse<?> getBrands() {
        List<BrandResponseDto> brandResponseDtos = brandRepository.findAll()
                .stream()
                .map(BrandResponseDto::new)
                .toList();

        // 중복 체크를 위한 Set 생성
        Set<String> distinctBrandResponseDtos = new HashSet<>();

        for(BrandResponseDto brandResponseDto:brandResponseDtos){
            distinctBrandResponseDtos.add(brandResponseDto.getBrandName());
        }

        return ApiResponse.of(SuccessStatus.BRAND_GET_SUCCESS, distinctBrandResponseDtos);
    }
}
