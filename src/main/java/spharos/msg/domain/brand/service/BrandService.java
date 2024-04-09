package spharos.msg.domain.brand.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.brand.dto.BrandDetailDto;
import spharos.msg.domain.brand.dto.BrandResponseDto;
import spharos.msg.domain.brand.entity.Brand;
import spharos.msg.domain.brand.repository.BrandRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    @Transactional(readOnly = true)
    public ApiResponse<List<BrandResponseDto>> getBrands() {
        List<BrandResponseDto> brandResponseDtos = brandRepository.findAll()
                .stream()
                .map(BrandResponseDto::new)
                .distinct()
                .toList();
        return ApiResponse.of(SuccessStatus.BRAND_GET_SUCCESS, brandResponseDtos);
    }
    public ApiResponse<BrandDetailDto> getBrandDetail(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow();
        return ApiResponse.of(SuccessStatus.BRAND_DETAIL_GET_SUCCESS, new BrandDetailDto(brand));
    }
}
