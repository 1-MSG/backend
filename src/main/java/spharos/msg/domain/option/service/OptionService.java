package spharos.msg.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.option.dto.OptionTypeDto;
import spharos.msg.domain.option.entity.Options;
import spharos.msg.domain.option.repository.OptionRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;

    public ApiResponse<?> getOptionType(Long optionId) {
        Options option = optionRepository.findById(optionId).orElseThrow();

        return ApiResponse.of(SuccessStatus.OPTION_TYPE_SUCCESS, new OptionTypeDto(option));
    }

    public ApiResponse<?> getOption(Long[] optionIds) {
        for(Long optionId:optionIds){
            Options option = optionRepository.findById(optionId).orElseThrow();
            if(option.getOptionLevel().equals(1)){
                //고민중
            }
        }
        return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,
                null);
    }
}
