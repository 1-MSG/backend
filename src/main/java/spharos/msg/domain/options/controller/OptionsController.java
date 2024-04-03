package spharos.msg.domain.options.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.options.service.OptionsService;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "Options", description = "옵션 관련 API")
@RequestMapping("/api/v1/option")
public class OptionsController {
    private final OptionsService optionsService;
    @Operation(summary = "상품 옵션 종류 조회",
            description = "상품 ID를 입력하면 해당 상품의 옵션 종류를 조회 합니다. (색상,사이즈,기타)")
    @GetMapping("/type/{productId}")
    public ApiResponse<?> getOptionsType(
            @PathVariable Long productId)
    {
        return optionsService.getOptionsType(productId);
    }
    @Operation(summary = "최상위 옵션 조회",
            description = "상품 ID를 입력하면 해당 상품의 최상위 옵션을 조회 합니다.")
    @GetMapping("/{productId}")
    public ApiResponse<?> getOptions(
            @PathVariable Long productId)
    {
        return optionsService.getOptions(productId);
    }
    @Operation(summary = "자식 옵션 조회",
            description = "최상위 옵션에 있는 옵션 ID를 선택 시 해당 옵션의 자식 옵션을 조회 합니다.")
    @GetMapping("/child/{optionsId}")
    public ApiResponse<?> getChildOptions(
            @PathVariable Long optionsId)
    {
        return optionsService.getChildOptions(optionsId);
    }
}
