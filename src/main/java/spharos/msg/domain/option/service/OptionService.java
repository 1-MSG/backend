package spharos.msg.domain.option.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.option.dto.OptionResponse;
import spharos.msg.domain.option.entity.Option;
import spharos.msg.domain.option.repository.OptionRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;

    public ApiResponse<?> getOptionType(Long optionId) {
        Option option = optionRepository.findById(optionId).orElseThrow();

        return ApiResponse.of(SuccessStatus.OPTION_TYPE_SUCCESS,
                OptionResponse.OptionTypeDto
                        .builder()
                        .optionLevel(option.getOptionLevel())
                        .optionType(option.getOptionType())
                        .build());
    }
}
