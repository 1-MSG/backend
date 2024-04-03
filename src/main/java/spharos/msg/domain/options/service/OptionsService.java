package spharos.msg.domain.options.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.options.dto.OptionTypeDto;
import spharos.msg.domain.options.dto.OptionsResponseDto;
import spharos.msg.domain.options.entity.Options;
import spharos.msg.domain.options.repository.OptionsRepository;
import spharos.msg.domain.product.entity.Product;
import spharos.msg.domain.product.entity.ProductOption;
import spharos.msg.domain.product.repository.ProductOptionRepository;
import spharos.msg.domain.product.repository.ProductRepository;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.code.status.SuccessStatus;
import spharos.msg.global.api.exception.OptionsException;
import spharos.msg.global.api.exception.ProductNotExistException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OptionsService {
    private final OptionsRepository optionsRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    public ApiResponse<?> getOptionType(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(ErrorStatus.NOT_EXIST_PRODUCT_ID));

        List<ProductOption> productOptions = productOptionRepository.findByProduct(product);

        List<OptionTypeDto> optionTypeDtos = new ArrayList<>();

        if(productOptions.get(0).getOptions().getOptionName()!=null){
            optionTypeDtos.add(new OptionTypeDto(productOptions.get(0).getOptions()));
        }
        Set<Long> parentIds = getParentOptionIds(productOptions); //1,2,3
        if(!parentIds.isEmpty()){
            Long parentId = parentIds.iterator().next();
            Options options = optionsRepository.findById(parentId).orElseThrow();
            optionTypeDtos.add(new OptionTypeDto(options));
        }
        //증조부확인

        return ApiResponse.of(SuccessStatus.OPTION_TYPE_SUCCESS, optionTypeDtos);
    }
    //최상위 옵션 조히
    public ApiResponse<?> getOptions(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(ErrorStatus.NOT_EXIST_PRODUCT_ID));

        List<ProductOption> productOptions = productOptionRepository.findByProduct(product);
        Set<Long> parentIds = getParentOptionIds(productOptions);
        List<OptionsResponseDto> optionTypeList = getParentOptionDetails(parentIds);
        List<OptionsResponseDto> parentOptionTypeList = getFinalParentOptionDetails(parentIds);

        if (parentOptionTypeList.isEmpty()) {
            return ApiResponse.of(SuccessStatus.OPTION_FIRST_SUCCESS, optionTypeList);
        }
        return ApiResponse.of(SuccessStatus.OPTION_ID_SUCCESS, parentOptionTypeList.stream().distinct());
    }

    //하위 옵션 데이터 조회
    public ApiResponse<?> getChildOptions(Long optionsId) {
        Options options = optionsRepository.findById(optionsId)
                .orElseThrow(() -> new OptionsException(ErrorStatus.NOT_EXIST_PRODUCT_OPTION));

        List<Options> childOptions = options.getChild();
        //하위 옵션이 없다면 던지고 있다면 해당 옵션 리스트 반환
        if (childOptions.isEmpty()) {
            throw new OptionsException(ErrorStatus.NOT_EXIST_CHILD_OPTION);
        }
        return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,
                childOptions.stream()
                        .map(option -> new OptionsResponseDto(option, productOptionRepository.findByOptions(option).get(0).getStock()))
                        .toList());
    }

    //상품이 가진 최하위 옵션 ID의 상위 옵션 ID 취합
    private Set<Long> getParentOptionIds(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(productOption -> productOption.getOptions().getParent().getId())
                .collect(Collectors.toSet());
    }


    private List<OptionTypeDto> getParentOptionTypes(Set<Long> parentIds) {
        return parentIds.stream()
                .map(optionId -> optionsRepository.findById(optionId)
                        .orElseThrow(() -> new OptionsException(ErrorStatus.NOT_EXIST_PRODUCT_OPTION)))
                .map(OptionTypeDto::new)
                .toList();
    }
    private List<OptionTypeDto> getFinalParentOptionTypes(Set<Long> parentIds) {
        return parentIds.stream()
                .map(optionId -> optionsRepository.findById(optionId)
                        .orElseThrow(() -> new OptionsException(ErrorStatus.NOT_EXIST_PRODUCT_OPTION)))
                .filter(options -> options.getParent() != null)
                .map(options -> new OptionTypeDto(options.getParent()))
                .toList();
    }

    //해당 ID들의 정보(옵션ID,이름,타입,레벨) 반환
    private List<OptionsResponseDto> getParentOptionDetails(Set<Long> parentIds) {
        return parentIds.stream()
                .map(optionId -> optionsRepository.findById(optionId)
                        .orElseThrow(() -> new OptionsException(ErrorStatus.NOT_EXIST_PRODUCT_OPTION)))
                .map(option -> new OptionsResponseDto(option, null))
                .toList();
    }
    //한단계 더 상위 옵션이 있는지 검증 및 있다면 해당 옵션 정보 반환
    private List<OptionsResponseDto> getFinalParentOptionDetails(Set<Long> parentIds) {
        return parentIds.stream()
                .map(optionId -> optionsRepository.findById(optionId)
                        .orElseThrow(() -> new OptionsException(ErrorStatus.NOT_EXIST_PRODUCT_OPTION)))
                .filter(options -> options.getParent() != null)
                .map(options -> new OptionsResponseDto(options.getParent(),null))
                .toList();
    }
}