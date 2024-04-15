package spharos.msg.domain.options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.options.dto.OptionTypeDto;
import spharos.msg.domain.options.dto.OptionsNameDto;
import spharos.msg.domain.options.dto.OptionsResponseDto;
import spharos.msg.domain.options.service.OptionsServiceV1;
import spharos.msg.global.api.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Options V1", description = "옵션 관련 API")
@RequestMapping("/api/v1/option")
public class OptionsControllerV1 {

    private final OptionsServiceV1 optionsService;

    @Operation(summary = "상품 옵션 종류 조회",
        description = "상품 ID를 입력하면 해당 상품의 옵션 종류를 조회 합니다. (색상,사이즈,기타)")
    @GetMapping("/type/{productId}")
    public ApiResponse<List<OptionTypeDto>> getOptionsType(
            @PathVariable Long productId) {
        return optionsService.getOptionsType(productId);
    }

    @Operation(summary = "최상위 옵션 조회",
        description = "상품 ID를 입력하면 해당 상품의 최상위 옵션을 조회 합니다.")
    @GetMapping("/first/{productId}")
    public ApiResponse<List<OptionsResponseDto>> getFirstOptions(
            @PathVariable Long productId) {
        return optionsService.getFirstOptions(productId);
    }

    @Operation(summary = "자식 옵션 조회",
            description = "최상위 옵션에 있는 옵션 ID를 선택 시 해당 옵션의 자식 옵션을 조회 합니다.")
    @GetMapping("/child/{optionsId}")
    public ApiResponse<List<OptionsResponseDto>> getChildOptions(
            @PathVariable Long optionsId) {
        return optionsService.getChildOptions(optionsId);
    }

    @Operation(summary = "ProductOptionId 조회",
            description = "ProductId를 받아서 ProductOptionId를 조회 합니다.")
    @GetMapping("/product-option/{productId}")
    public ApiResponse<Long> getProductOptionId(
            @PathVariable Long productId) {
        return optionsService.getProductOptionId(productId);
    }

    @Operation(summary = "옵션명 조회",
            description = "상품의 전체 옵션 종류, 이름을 조회 합니다. (색상 : 빨강, 사이즈 : L)")
    @GetMapping
    public ApiResponse<List<OptionsNameDto>> getOptionsDetail(
            @RequestParam(value = "productOptionId") Long productOptionId)
    {
        return optionsService.getOptionsDetail(productOptionId);
    }
}
