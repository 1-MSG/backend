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
    @Operation(summary = "옵션 종류 조회",
            description = "상품의 옵션 종류와 레벨을 조회 합니다")
    @GetMapping
    public ApiResponse<?> getOptionType(
            @RequestParam(value = "optionIds") Long[] optionIds
    ){
        return optionsService.getOptionType(optionIds);
    }
    @Operation(summary = "옵션 첫번째 항목 조회",
            description = "상품이 가지는 옵션의 첫번째 항목을 조회합니다.")
    @GetMapping("/first-option")
    public ApiResponse<?> getFirstOption(
            @RequestParam(value = "optionIds") Long[] optionIds
    ){
        return optionsService.getFirstOption(optionIds);
    }
    @Operation(summary = "자식 옵션 조회",
            description = "옵션을 선택했을 때 해당 옵션의 자식 옵션을 조회 합니다.")
    @GetMapping("/{optionId}")
    public ApiResponse<?> getOptionChild(
            @PathVariable Long optionId)
    {
        return optionsService.getOptionChild(optionId);
    }

    @Operation(summary = "첫 옵션 조회v2",
            description = "옵션을 선택했을 때 해당 옵션의 자식 옵션을 조회 합니다.")
    @GetMapping("/product/{productId}")
    public ApiResponse<?> getOptionsV2(
            @PathVariable Long productId)
    {
        return optionsService.getOptionsV2(productId);
    }

    @Operation(summary = "자식 옵션 조회v2",
            description = "옵션을 선택했을 때 해당 옵션의 자식 옵션을 조회 합니다.v2")
    @GetMapping("/product/child/{optionsId}")
    public ApiResponse<?> getChildOptionsV2(
            @PathVariable Long optionsId)
    {
        return optionsService.getChildOptionsV2(optionsId);
    }
}
