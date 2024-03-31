package spharos.msg.domain.search.service;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.search.dto.SearchResponse.SearchInputDto;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDto;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDtos;
import spharos.msg.domain.search.repository.SearchRepository;
import spharos.msg.domain.search.utils.SearchKeyword.SearchKeywordBuilder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private static final String KEYWORD_DELIMITER = " ";

    private final SearchRepository searchRepository;

    public SearchProductDtos findMatchProducts(String keyword, Pageable pageable) {
        keyword = getSearchKeyword(keyword);

        Page<SearchProductDto> searched = searchRepository.searchAllProduct(keyword,
            pageable);

        return SearchProductDtos
            .builder()
            .searchProductDtos(searched.getContent())
            .isLast(searched.isLast())
            .build();
    }

    public List<SearchInputDto> findExpectedKeywords(String keyword) {
        keyword = trimKeyword(keyword);

        List<SearchInputDto> searchInputDtos = new ArrayList<>();
        List<String> essentialProductNames = searchRepository
            .searchAllKeyword(keyword)
            .stream()
            .map(product -> trimKeyword(product.getProductName()))
            .toList();
        for (String essentialProductName : essentialProductNames) {
            List<String> searchWords = getSearchWords(essentialProductName, keyword);
            searchInputDtos.addAll(toSearchInputDto(searchWords));
        }
        return searchInputDtos
            .stream()
            .distinct()
            .sorted(comparingInt(w -> w.getProductName().length()))
            .toList();
    }

    private String trimKeyword(String keyword) {
        return keyword
            .replaceAll(REMOVE_OVER_TWO_BLANK, KEYWORD_DELIMITER)
            .replaceAll(ONLY_WORD_NUMBER_BLANK, REMOVE)
            .trim();
    }

    private List<String> getSearchWords(String essentialName, String keyword) {
        int startIndex = essentialName.indexOf(keyword);
        List<String> splitWords = Arrays.asList(essentialName.split(KEYWORD_DELIMITER));
        return getWordsWithinKeyword(essentialName, startIndex, splitWords);
    }

    private List<String> getWordsWithinKeyword(
        String essentialName,
        int startIndex,
        List<String> splitWords) {

        List<String> resultWords = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        splitWords.stream()
            .filter(word -> essentialName.indexOf(word) >= startIndex)
            .forEach(word -> addToResultWord(resultWords, stringBuilder, word));

        return resultWords;
    }

    private void addToResultWord(
        List<String> resultWords, StringBuilder stringBuilder, String word) {
        String resultWord = stringBuilder
            .append(KEYWORD_DELIMITER)
            .append(word)
            .toString();

        resultWords.add(resultWord);
    }

    private List<SearchInputDto> toSearchInputDto(List<String> searchWords) {
        return searchWords
            .stream()
            .map(word -> SearchInputDto
                .builder()
                .productName(word)
                .build())
            .toList();
    }
}
