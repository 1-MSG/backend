package spharos.msg.domain.option.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.option.service.OptionService;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/option")
public class OptionController {
    private final OptionService optionService;
    @GetMapping("/type/{optionId}")
    public ApiResponse<?> getOptionType(
            @PathVariable Long optionId
    ){
        return optionService.getOptionType(optionId);
    }
    @GetMapping
    public ApiResponse<?> getOption(
            @RequestParam(value = "optionIds") Long[] optionIds
    ){
        return optionService.getOption(optionIds);
    }

}
