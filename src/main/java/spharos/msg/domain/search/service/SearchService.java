package spharos.msg.domain.search.service;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDto;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDtos;
import spharos.msg.domain.search.dto.SearchResponse.SearchTextDto;
import spharos.msg.domain.search.repository.SearchRepository;
import spharos.msg.domain.search.utils.KeywordModifier.SearchKeywordBuilder;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.SearchException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private static final String KEYWORD_DELIMITER = " ";
    private static final int FIRST_WORD = 0;

    private final SearchRepository searchRepository;

    public SearchProductDtos findMatchProducts(String keyword, Pageable pageable) {
        keyword = filterText(keyword);
        if (keyword.isEmpty() || keyword.isBlank()) {
            throw new SearchException(ErrorStatus.INVALID_SEARCH_KEYWORD);
        }

        Page<SearchProductDto> searched = searchRepository.searchAllProduct(keyword, pageable);

        return SearchProductDtos
            .builder()
            .responseTime(String.valueOf(System.currentTimeMillis()))
            .searchProductDtos(searched.getContent())
            .isLast(searched.isLast())
            .build();
    }

    public List<SearchTextDto> findExpectedKeywords(String keyword) {
        String searchWord = filterText(keyword);
        if (searchWord.isEmpty() || searchWord.isBlank()) {
            throw new SearchException(ErrorStatus.INVALID_SEARCH_KEYWORD);
        }
        List<SearchTextDto> originTexts = searchRepository.searchAllKeyword(searchWord);

        return originTexts
            .stream()
            .map(text -> cutIntoPieces(text.getProductName(), searchWord))
            .flatMap(Collection::stream)
            .sorted(comparingInt(t -> t.getProductName().length()))
            .distinct()
            .toList();
    }

    private List<SearchTextDto> cutIntoPieces(String originText, String searchWord) {
        List<SearchTextDto> result = new ArrayList<>();
        StringBuilder searchText = new StringBuilder();

        originText = filterText(originText);
        int startIndex = findStartIndex(searchWord, originText);
        String[] splitedText = originText.split(KEYWORD_DELIMITER);

        for (String text : splitedText) {
            if (originText.indexOf(text) >= startIndex) {
                result.add(addSearchText(searchText, text));
            }
        }
        return result;
    }

    private int findStartIndex(String searchWord, String originText) {
        int index = originText.indexOf(searchWord);
        while (index > 0 && originText.charAt(index) != KEYWORD_DELIMITER.charAt(0)) {
            index--;
        }
        return index;
    }

    private SearchTextDto addSearchText(StringBuilder searchText, String text) {
        StringBuilder addWord = (searchText.length() == FIRST_WORD) ?
            searchText.append(text) :
            searchText.append(KEYWORD_DELIMITER + text);

        return new SearchTextDto(addWord.toString());
    }

    private String filterText(String keyword) {
        return SearchKeywordBuilder
            .withKeyword(keyword)
            .removeDuplicatedSpaces()
            .removeUnnecessary()
            .removeUnCompletedKorean()
            .toLowerCase()
            .trimSpaces()
            .build()
            .getKeyword();
    }
}
