package spharos.msg.domain.search.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDtos;
import spharos.msg.domain.search.dto.SearchResponse.SearchTextDto;
import spharos.msg.domain.search.service.SearchServiceV2;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v2/")
@Tag(name = "Search", description = "검색 API")
@Slf4j
public class SearchControllerV2 {

    private final SearchServiceV2 searchService;

    @GetMapping("search")
    public ApiResponse<SearchProductDtos> searchResultAPI(
        @RequestParam(value = "keyword") String keyword,
        @PageableDefault(size = 10, page = 0) Pageable pageable) {
        SearchProductDtos searchProductDtos = searchService.findMatchProducts(keyword, pageable);
        return ApiResponse.of(SuccessStatus.SEARCH_RESULT_SUCCESS, searchProductDtos);
    }

    @GetMapping("search-list")
    public ApiResponse<List<SearchTextDto>> searchInputDto(
        @RequestParam(value = "keyword") String keyword
    ) {
        List<SearchTextDto> searchTextDtos = searchService.findExpectedKeywords(keyword);
        return ApiResponse.of(SuccessStatus.SEARCH_INPUT_SUCCESS, searchTextDtos);
    }
}
