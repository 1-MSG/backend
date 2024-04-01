package spharos.msg.domain.option.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
