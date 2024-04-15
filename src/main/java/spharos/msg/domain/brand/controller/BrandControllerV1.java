package spharos.msg.domain.brand.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.brand.dto.BrandDetailDto;
import spharos.msg.domain.brand.dto.BrandResponseDto;
import spharos.msg.domain.brand.service.BrandServiceV1;
import spharos.msg.global.api.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brand")
@Tag(name = "Brand", description = "브랜드 API")
@RequiredArgsConstructor
public class BrandControllerV1 {
    private final BrandServiceV1 brandService;

    @Operation(summary = "전체 브랜드 조회",
            description = "모든 브랜드 이름을 조회합니다")
    @GetMapping
    public ApiResponse<List<BrandResponseDto>> getBrands(){
        return brandService.getBrands();
    }
    @Operation(summary = "브랜드 상세 조회",
            description = "브랜드 id를 입력 받아 브랜드명과 무료 배송 최소 금액을 조회 합니다")
    @GetMapping("/{brandId}")
    public ApiResponse<BrandDetailDto> getBrandDetail(
            @PathVariable Long brandId
    ){
        return brandService.getBrandDetail(brandId);
    }
}
