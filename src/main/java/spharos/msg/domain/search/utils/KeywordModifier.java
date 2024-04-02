package spharos.msg.domain.search.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class KeywordModifier {

    private static final String UNNESSARY_WORD = "[^a-zA-Z가-힣0-9\\s-]";
    private static final String KEYWORD_DELIMITER = " ";
    private static final String REMOVE = "";
    private static final String REMOVE_OVER_TWO_BLANK = "\\s{2,}";
    private static final String KOREAN_CONSONANTS = "[\\u3131-\\u314E]";
    private static final String KOREAN_VOWELS = "[\\u3131-\\u314E]";

    private final String keyword;

    private KeywordModifier(String keyword) {
        this.keyword = keyword;
    }

    @AllArgsConstructor
    public static class SearchKeywordBuilder {

        private String keyword;

        public static SearchKeywordBuilder withKeyword(String keyword) {
            return new SearchKeywordBuilder(keyword);
        }

        public KeywordModifier build() {
            return new KeywordModifier(keyword);
        }

        public SearchKeywordBuilder trimSpaces() {
            this.keyword = keyword.trim();
            return this;
        }

        public SearchKeywordBuilder removeDuplicatedSpaces() {
            this.keyword = keyword.replaceAll(REMOVE_OVER_TWO_BLANK, KEYWORD_DELIMITER);
            return this;
        }

        public SearchKeywordBuilder removeUnnecessary() {
            this.keyword = keyword.replaceAll(UNNESSARY_WORD, REMOVE);
            return this;
        }

        public SearchKeywordBuilder toLowerCase() {
            this.keyword = keyword.toLowerCase();
            return this;
        }

        public SearchKeywordBuilder removeUnCompletedKorean() {
            this.keyword = keyword.replaceAll(KOREAN_CONSONANTS + KOREAN_VOWELS, REMOVE);
            return this;
        }
    }

}
