package spharos.msg.domain.options.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.options.dto.OptionsNameDto;
import spharos.msg.domain.options.entity.Options;
import spharos.msg.domain.options.repository.OptionsRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.code.status.SuccessStatus;
import spharos.msg.global.api.exception.OptionsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionsService {
    private final OptionsRepository optionsRepository;

    //상품의 옵션 종류 조회 (옵션레벨:옵션타입) -> 1:사이즈/2:색상
    public ApiResponse<?> getOptionType(Long[] optionIds) {
        HashMap<Integer, String> optionsHashMap = new HashMap<>();
        for (Long optionId : optionIds) {
            Options option = optionsRepository.findById(optionId).orElseThrow();
            optionsHashMap.put(option.getOptionLevel(), option.getOptionType());
        }
        return ApiResponse.of(SuccessStatus.OPTION_TYPE_SUCCESS, optionsHashMap);
    }

    //옵션 첫번째 항목 리스트(id,옵션명)
    public ApiResponse<?> getFirstOption(Long[] optionIds) {
        List<Options> firstOptions = new ArrayList<>();
        for (Long optionId : optionIds) {
            Options option = optionsRepository.findById(optionId).orElseThrow();
            if (option.getOptionLevel().equals(1)) {
                firstOptions.add(option);
            }
        }
        if (firstOptions.isEmpty()) {
            throw new OptionsException(ErrorStatus.NOT_EXIST_PRODUCT_OPTION);
        }
        return ApiResponse.of(SuccessStatus.OPTION_FIRST_SUCCESS,
                firstOptions
                        .stream()
                        .map(OptionsNameDto::new)
                        .toList());
    }

    //해당 옵션에게 자식이 있다면 옵션의 자식 리스트 반환
    public ApiResponse<?> getOptionChild(Long parentId) {
        List<Options> options = optionsRepository.findOptionsByParentId(parentId);
        if (options.isEmpty()) {
            throw new OptionsException(ErrorStatus.NOT_EXIST_PRODUCT_OPTION);
        }
        return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,
                 options.stream()
                        .map(OptionsNameDto::new)
                        .toList());
    }
}