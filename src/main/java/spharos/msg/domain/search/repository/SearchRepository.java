package spharos.msg.domain.search.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.search.dto.SearchResponse.SearchInputDto;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDto;

@Repository
public interface SearchRepository {

    Page<SearchProductDto> searchAllProduct(String keyword, Pageable pageable);

    List<SearchInputDto> searchAllKeyword(String keyword);
}
