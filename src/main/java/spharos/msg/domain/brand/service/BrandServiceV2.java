package spharos.msg.domain.brand.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.brand.dto.BrandDetailDto;
import spharos.msg.domain.brand.dto.BrandResponseDto;
import spharos.msg.domain.brand.repository.BrandRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandServiceV2 {
    private final BrandRepository brandRepository;
    public ApiResponse<List<BrandResponseDto>> getBrands() {
        return ApiResponse.of(SuccessStatus.BRAND_GET_SUCCESS, brandRepository.findAllBrandNames());
    }
    public ApiResponse<BrandDetailDto> getBrandDetail(Long brandId) {
        return ApiResponse.of(SuccessStatus.BRAND_DETAIL_GET_SUCCESS, brandRepository.getBrandDetail(brandId));
    }
}
