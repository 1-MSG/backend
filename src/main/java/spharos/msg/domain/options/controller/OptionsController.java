package spharos.msg.domain.options.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.options.service.OptionsService;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/option")
public class OptionsController {
    private final OptionsService optionsService;

    @GetMapping
    public ApiResponse<?> getOptionType(
            @RequestParam(value = "optionIds") Long[] optionIds
    ){
        return optionsService.getOptionType(optionIds);
    }

    @GetMapping("/first-option")
    public ApiResponse<?> getFirstOption(
            @RequestParam(value = "optionIds") Long[] optionIds
    ){
        return optionsService.getFirstOption(optionIds);
    }
    @GetMapping("/optionName/{optionId}")
    public ApiResponse<?> getOptionChild(
            @PathVariable Long optionId)
    {
        return optionsService.getOptionChild(optionId);
    }
}
