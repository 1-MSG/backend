package spharos.msg.domain.options.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.options.converter.OptionsConverter;
import spharos.msg.domain.options.dto.OptionTypeDto;
import spharos.msg.domain.options.dto.OptionsNameDto;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static spharos.msg.domain.options.converter.OptionsConverter.toDto;
import static spharos.msg.domain.options.converter.OptionsConverter.toNameDto;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OptionsServiceV2 {
    private static final String OPTION_DELIMETER = "/";
    private final OptionsRepository optionsRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    //상품 옵션 종류 조회
    public ApiResponse<List<OptionTypeDto>> getOptionsType(Long productId) {
        Product product = productRepository.getReferenceById(productId);
        List<ProductOption> productOptions = productOptionRepository.findByProduct(product);
        List<OptionTypeDto> optionTypeDtos = new ArrayList<>();

        //옵션 없는 상품
        ProductOption firstProductOption = productOptions.get(0);
        Options options = firstProductOption.getOptions();
        if (options == null || options.getOptionName() == null) {
            return ApiResponse.of(SuccessStatus.OPTION_TYPE_SUCCESS, optionTypeDtos);
        }
        //현재 옵션 저장
        OptionTypeDto optionTypeDto = productOptionRepository.findTypeByOptions(options);
        if (optionTypeDto.getOptionType() != null) {
            optionTypeDtos.add(optionTypeDto);
        }
        //상위 옵션 저장
        if (options.getParent() != null) {
            Set<Long> parentIds = getParentOptionIds(productOptions);
            options = optionsRepository.getReferenceById(parentIds.iterator().next());
            optionTypeDtos.add(productOptionRepository.findTypeByOptions(options));
        }
        return ApiResponse.of(SuccessStatus.OPTION_TYPE_SUCCESS, optionTypeDtos);
    }

    //최상위 옵션 조회
    public ApiResponse<List<OptionsResponseDto>> getFirstOptions(Long productId) {
        Product product = productRepository.getReferenceById(productId);
        List<ProductOption> productOptions = productOptionRepository.findByProduct(product);
        if (productOptions.get(0).getOptions() == null || productOptions.get(0).getOptions().getOptionName() == null) {
            return ApiResponse.of(SuccessStatus.OPTION_FIRST_SUCCESS,
                    productOptions.stream()
                            .map(productOption -> toDto(productOption.getId(), productOption.getStock()))
                            .toList());
        }
        if (productOptions.get(0).getOptions().getParent() == null) {
            return ApiResponse.of(SuccessStatus.OPTION_FIRST_SUCCESS,
                    productOptions.stream()
                            .map(OptionsConverter::toDto)
                            .toList());
        }
        Set<Long> parentIds = getParentOptionIds(productOptions);
        List<OptionsResponseDto> optionDetailList = getParentOptionDetails(parentIds);
        List<OptionsResponseDto> parentOptionDetailList = getFinalParentOptionDetails(parentIds);

        if (parentOptionDetailList.isEmpty()) {
            return ApiResponse.of(SuccessStatus.OPTION_FIRST_SUCCESS, optionDetailList);
        }
        return ApiResponse.of(SuccessStatus.OPTION_ID_SUCCESS,
                parentOptionDetailList.stream().distinct().toList());
    }

    //상품 옵션 조회
    public ApiResponse<List<OptionsNameDto>> getOptionsDetail(Long productOptionId) {
        ProductOption productOption = productOptionRepository.getReferenceById(productOptionId);
        Options options = productOption.getOptions();
        int stock = productOption.getStock();
        List<OptionsNameDto> optionsNameDtos = new ArrayList<>();
        if (options == null || options.getOptionName() == null) {
            optionsNameDtos.add(toNameDto(stock));
            return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS, optionsNameDtos);
        }

        optionsNameDtos.add(toNameDto(options, stock));

        while (options.getParent() != null) {
            optionsNameDtos.add(toNameDto(options.getParent(), stock));
            options = options.getParent();
        }
        return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS, optionsNameDtos);
    }

    //구매쪽 옵션
    public String getOptions(Long productOptionId) {
        ProductOption productOption = productOptionRepository.getReferenceById(productOptionId);
        Options options = productOption.getOptions();
        List<String> optionNames = new ArrayList<>();

        optionNames.add(options.getOptionName());

        while (options.getParent() != null) {
            optionNames.add(options.getParent().getOptionName());
            options = options.getParent();
        }
        return String.join(OPTION_DELIMETER, optionNames);
    }

    //하위 옵션 데이터 조회
    public ApiResponse<List<OptionsResponseDto>> getChildOptions(Long optionsId) {
        Options options = optionsRepository.getReferenceById(optionsId);
        List<Options> childOptions = options.getChild();
        //하위 옵션이 없다면 던지고 있다면 해당 옵션 리스트 반환
        if (childOptions.isEmpty()) {
            throw new OptionsException(ErrorStatus.NOT_EXIST_CHILD_OPTION);
        }
        return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,
                childOptions.stream()
                        .map(option -> {
                            ProductOption productOption = productOptionRepository.findByOptions(option);
                            return toDto(option, productOption.getId(), productOption.getStock());
                        })
                        .toList());
    }


    //상품이 가진 최하위 옵션 ID의 상위 옵션 ID 취합
    Set<Long> getParentOptionIds(List<ProductOption> productOptions) {
        return productOptionRepository.getParentOptionIds(productOptions);
    }

    //해당 ID들의 정보(옵션ID,이름,타입,레벨) 반환
    private List<OptionsResponseDto> getParentOptionDetails(Set<Long> parentIds) {
        List<OptionsResponseDto> optionsResponseDtos = new ArrayList<>();
        for (Long parentId : parentIds) {
            Options options = optionsRepository.getReferenceById(parentId);
            optionsResponseDtos.add(toDto(options, null, null));
        }
        return optionsResponseDtos;
    }

    //한단계 더 상위 옵션이 있는지 검증 및 있다면 해당 옵션 정보 반환
    private List<OptionsResponseDto> getFinalParentOptionDetails(Set<Long> parentIds) {
        Set<Long> grandParentIds = new HashSet<>();
        List<OptionsResponseDto> optionsResponseDtos = new ArrayList<>();

        for (Long parentId : parentIds) {
            Options options = optionsRepository.getReferenceById(parentId);
            if (options.getParent() != null) {
                grandParentIds.add(options.getParent().getId());
            }
        }

        for (Long grandParentId : grandParentIds) {
            Options options = optionsRepository.getReferenceById(grandParentId);
            optionsResponseDtos.add(toDto(options, null, null));
        }
        return optionsResponseDtos;
    }

    //옵션 없는 상품용 api
    public ApiResponse<Long> getProductOptionId(Long productId) {
        Product product = productRepository.getReferenceById(productId);
        List<ProductOption> productOptions = productOptionRepository.findByProduct(product);
        return ApiResponse.of(SuccessStatus.PRODUCT_OPTION_ID_GET_SUCCESS, productOptions.get(0).getId());
    }
}
