package spharos.msg.domain.options.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import spharos.msg.domain.options.dto.OptionsNameDto;
import spharos.msg.domain.options.dto.OptionsResponseDto;
import spharos.msg.domain.options.entity.Options;
import spharos.msg.domain.product.entity.ProductOption;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionsConverter {
    static public OptionsResponseDto toDto (Options options, Long id, Integer stock){
        return OptionsResponseDto.builder()
                .optionId(options.getId())
                .productOptionId(id)
                .optionName(options.getOptionName())
                .optionType(options.getOptionType())
                .optionLevel(options.getOptionLevel())
                .stock(stock)
                .build();
    }
    static public OptionsResponseDto toDto(ProductOption productOption){
        return OptionsResponseDto.builder()
                .optionId(productOption.getOptions().getId())
                .productOptionId(productOption.getId())
                .optionName(productOption.getOptions().getOptionName())
                .optionType(productOption.getOptions().getOptionType())
                .optionLevel(productOption.getOptions().getOptionLevel())
                .stock(productOption.getStock())
                .build();
    }
    static public OptionsResponseDto toDto(Long productOptionId,int stock){
        return OptionsResponseDto.builder()
                .productOptionId(productOptionId)
                .stock(stock)
                .build();
    }

    static public OptionsResponseDto toDto (String optionName, Integer stock){
        return OptionsResponseDto.builder()
                .optionName(optionName)
                .stock(stock)
                .build();
    }

    static public OptionsNameDto toNameDto(Integer stock){
        return OptionsNameDto.builder()
                .optionsType("")
                .optionsName("")
                .stock(stock)
                .build();
    }
    static public OptionsNameDto toNameDto(Options options,Integer stock){
        return OptionsNameDto.builder()
                .optionsType(options.getOptionType())
                .optionsName(options.getOptionName())
                .stock(stock)
                .build();
    }
}
