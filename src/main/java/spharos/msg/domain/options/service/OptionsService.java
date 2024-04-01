package spharos.msg.domain.options.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.options.dto.OptionsTypeDto;
import spharos.msg.domain.options.entity.Options;
import spharos.msg.domain.options.repository.OptionsRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Service
@RequiredArgsConstructor
public class OptionsService {
    private final OptionsRepository optionsRepository;

    public ApiResponse<?> getOptionType(Long optionId) {
        Options option = optionsRepository.findById(optionId).orElseThrow();

        return ApiResponse.of(SuccessStatus.OPTION_TYPE_SUCCESS, new OptionsTypeDto(option));
    }

    public ApiResponse<?> getOption(Long[] optionIds) {
        for(Long optionId:optionIds){
            Options option = optionsRepository.findById(optionId).orElseThrow();
            if(option.getOptionLevel().equals(1)){
                //고민중
            }
        }
        return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,
                null);
    }
}
