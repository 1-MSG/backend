package spharos.msg.domain.category.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDtos;
import spharos.msg.domain.category.repository.CategoryProductRepository;

@SpringBootTest
@Slf4j
@Transactional
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryProductRepository categoryProductRepository;

    @ParameterizedTest
    @ValueSource(ints = {10, 11, 12, 13, 14})
    @DisplayName("해당 페이지가 마지막이라면 isLast는 true, 그 외에는 false 이어야 한다.")
    void CategoryServiceTest(int offset) {
        //given
        PageRequest pageable = PageRequest.of(offset, 10);
        Page<CategoryProductDto> categoryProductsById = categoryProductRepository
            .findCategoryProductsById(1L, pageable);
        //when
        CategoryProductDtos categoryProducts = categoryService
            .findCategoryProducts(1L, pageable);
        //then
        Assertions.assertThat(categoryProducts.getIsLast())
            .isEqualTo(categoryProductsById.isLast());
    }
}