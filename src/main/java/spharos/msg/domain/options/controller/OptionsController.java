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
    @GetMapping("/type/{optionId}")
    public ApiResponse<?> getOptionType(
            @PathVariable Long optionId
    ){
        return optionsService.getOptionType(optionId);
    }
    @GetMapping
    public ApiResponse<?> getOption(
            @RequestParam(value = "optionIds") Long[] optionIds
    ){
        return optionsService.getOption(optionIds);
    }

}
