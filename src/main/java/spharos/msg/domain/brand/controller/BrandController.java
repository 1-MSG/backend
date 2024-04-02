package spharos.msg.domain.brand.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.brand.service.BrandService;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequestMapping("/api/v1/brand")
@Tag(name = "Brand", description = "브랜드 API")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @Operation(summary = "전체 브랜드 조회",
            description = "모든 브랜드 이름을 조회합니다")
    @GetMapping
    public ApiResponse<?> getBrands(){
        return brandService.getBrands();
    }
}
