package spharos.msg.domain.options.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spharos.msg.domain.options.dto.OptionsNameDto;
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

import java.util.*;

@Service
@RequiredArgsConstructor
public class OptionsService {
    private final OptionsRepository optionsRepository;

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

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
            if (option.getParent() == null) {
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
        Options parentOption = optionsRepository.findById(parentId).orElseThrow();
        //자식이 없을 경우
        if (options.isEmpty()) {
            return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,
                    new OptionsNameDto(parentOption));
        }
        //있을 경우
        return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,
                 options.stream()
                        .map(OptionsNameDto::new)
                        .toList());
    }

    //최상위 옵션 불러오기
    public ApiResponse<?> getOptionsV2(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<ProductOption> productOptions = productOptionRepository.findByProduct(product);
        Set<Long> parentIds = new HashSet<>();
        List<String> optionNameList = new ArrayList<>();
        List<String> parentOtionNameList = new ArrayList<>();


        for(ProductOption productOption:productOptions){
            parentIds.add(productOption.getOption().getParent().getId());
        }

        for(Long optionId:parentIds) {
            Options options = optionsRepository.findById(optionId).orElseThrow();
            optionNameList.add(options.getOptionName());
        }

        for(Long optionId:parentIds){
            Options options = optionsRepository.findById(optionId).orElseThrow();
            if(options.getParent()==null){
                return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,optionNameList);
            }else{
                parentOtionNameList.add(options.getParent().getOptionName());
            }
        }
    //todo 리턴값 dto로 만들기 (id,타입 포함되게)
        return ApiResponse.of(SuccessStatus.OPTION_ID_SUCCESS,
                parentOtionNameList.stream().distinct());
    }
    //자식 데이터 넣기
    public ApiResponse<?> getChildOptionsV2(Long optionsId) {
        Options options = optionsRepository.findById(optionsId).orElseThrow();
        List<Options> childOptions = new ArrayList<>();
        if(options.getChild()!=null){
            childOptions.addAll(options.getChild());
        }
        return ApiResponse.of(SuccessStatus.OPTION_DETAIL_SUCCESS,
                childOptions.stream().map(OptionsNameDto::new).toList());
    }
}