package spharos.msg.domain.search.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.product.entity.Product;

@SpringBootTest
@Transactional
@Slf4j
class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;

    @Test
    @DisplayName("특정 브랜드 이름이 포함되면 그 브랜드의 Product를 반환한다.")
    void 상품_브랜드_검색_성공_테스트() {
        //given
        List<Product> result = searchRepository.searchAllProduct("탑텐", 0);
        //when, then
        assertThat(result)
            .isNotEmpty()
            .allMatch(e -> e.getProductBrand().contains("탑텐"));
    }

    @Test
    @DisplayName("특정 이름이 포함되면 그 이름이 포함된 Product를 반환한다.")
    void 상품_이름_검색_성공_테스트() {
        //given
        List<Product> result = searchRepository.searchAllProduct("남성", 0);
        //when, then
        assertThat(result)
            .isNotEmpty()
            .allMatch(e -> e.getProductName().contains("남성"));
    }

    @Test
    @DisplayName("검색 상품이 10개가 넘을시, 10개까지만 반환하도록 한다.")
    void 상품_개수_성공_테스트() {
        //given
        List<Product> result = searchRepository.searchAllProduct("남성", 0);
        //when, then
        assertThat(result).hasSize(10);
    }

    @Test
    @DisplayName("검색 상품이 10개가 넘을시, 10개까지만 반환하도록 한다.")
    void 마지막_스크롤_성공_테스트() {
        List<Product> result = searchRepository.searchAllProduct("남성", 5);
        System.out.println("result = " + result.size());
    }

    @Test
    @DisplayName("현재 인덱스와 다음 인덱스의 제품이 중복되면 안된다")
    void 인덱스_조회_성공_테스트() {
        List<Product> result1 = searchRepository.searchAllProduct("남성", 0);
        List<Product> result2 = searchRepository.searchAllProduct("남성", 1);

        assertThat(result1).noneMatch(result2::contains);
    }

    @Test
    @DisplayName("존재하지 않는 상품이면, 빈 리스트를 반환한다.")
    void 빈_리스트_반환() {
        List<Product> result = searchRepository.searchAllProduct("존재하지 않는 상품 ", 0);
        assertThat(result).isEmpty();
    }
}