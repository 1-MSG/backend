package spharos.msg.domain.search.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDto;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDtos;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchConverter {

    public static SearchProductDtos toDto(Page<SearchProductDto> searched, String responseTime) {
        return SearchProductDtos
            .builder()
            .responseTime(responseTime)
            .searchProductDtos(searched.getContent())
            .isLast(searched.isLast())
            .build();
    }
}
