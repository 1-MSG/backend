package spharos.msg.domain.search.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDto;
import spharos.msg.domain.search.dto.SearchResponse.SearchTextDto;

@Repository
public interface SearchRepository {

    Page<SearchProductDto> searchAllProductV1(String keyword, Pageable pageable);

    List<SearchTextDto> searchAllKeywordV1(String keyword);

    List<SearchTextDto> searchAllKeywordV2(String keyword);
}
