package spharos.msg.domain.category.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;
import spharos.msg.domain.category.entity.Category;

@SpringBootTest
@Transactional
class CategoryProductRepositoryTest {

    @Autowired
    CategoryProductRepository categoryProductRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private List<Category> mapToCategoryProduct(List<CategoryDto> categories) {
        return categories.stream()
            .map(e -> categoryRepository.findById(e.getCategoryId()).get())
            .toList();
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 20L})
    @DisplayName("카테고리 레벨이 0인걸 검색했다면, 검색 결과는 레벨이 1인게 나와야한다.")
    void 카테고리_조회_성공(Long categoryId) {
        //given
        List<CategoryDto> categories = categoryProductRepository
            .findCategoriesByParentId(categoryId);

        Integer parentLevel = categoryRepository
            .findById(categoryId)
            .get()
            .getProductCategoryLevel();

        //when
        List<Category> categoryProducts = mapToCategoryProduct(categories);
        List<Integer> levels = categoryProducts.stream()
            .map(Category::getProductCategoryLevel)
            .toList();
        //then
        Assertions.assertThat(levels)
            .isNotEmpty()
            .allMatch(e -> e.equals(parentLevel + 1));
    }

    @Test
    @DisplayName("페이지 간의 중복된 상품ID가 없어야한다.")
    void 카테고리_상품_조회_페이징_테스트() {
        //given
        List<Long> result1 = getProductIds(0, 1L);
        List<Long> result2 = getProductIds(1, 1L);

        //when,then
        Assertions.assertThat(result1)
            .isNotEmpty()
            .doesNotContainAnyElementsOf(result2);
    }

    private List<Long> getProductIds(int offset, long categoryId) {
        return categoryProductRepository
            .findCategoryProductsById(categoryId, PageRequest.of(offset, 10))
            .getContent()
            .stream()
            .map(CategoryProductDto::getProductId)
            .toList();
    }


}