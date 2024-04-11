package spharos.msg.domain.options.service;

import spharos.msg.domain.options.dto.OptionsNameDto;
import spharos.msg.global.api.ApiResponse;

import java.util.List;


public interface OptionsService {
    public ApiResponse<?> getOptionsType(Long productId);

    public ApiResponse<?> getFirstOptions(Long productId);

    public ApiResponse<List<OptionsNameDto>> getOptionsDetail(Long productOptionId);

    public String getOptions(Long productOptionId);
    public ApiResponse<?> getChildOptions(Long optionsId);
    public ApiResponse<?> getProductOptionId(Long productId);
}