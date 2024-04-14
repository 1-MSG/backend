package spharos.msg.domain.search.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import spharos.msg.domain.category.entity.QCategory;
import spharos.msg.domain.category.entity.QCategoryProduct;
import spharos.msg.domain.product.entity.QProduct;
import spharos.msg.domain.search.dto.QSearchResponse_SearchProductDto;
import spharos.msg.domain.search.dto.SearchResponse.SearchProductDto;
import spharos.msg.domain.search.dto.SearchResponse.SearchTextDto;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SearchRepositoryImpl implements SearchRepository {

    private static final int SEARCH_SIZE = 5;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<SearchProductDto> searchAllProductV1(String keyword, Pageable pageable) {
        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;

        Long total = getTotal(keyword, categoryProduct, product, category);

        List<SearchProductDto> searchProductDtos = getSearchProductIds(
            keyword, pageable, categoryProduct, product, category);
        return new PageImpl<>(searchProductDtos, pageable, total);
    }

    private Long getTotal(String keyword, QCategoryProduct categoryProduct, QProduct product,
        QCategory category) {
        return jpaQueryFactory
            .select(categoryProduct.countDistinct())
            .from(categoryProduct)
            .innerJoin(categoryProduct.product, product)
            .innerJoin(categoryProduct.category, category)
            .where(validateContainsKeyword(keyword, product, category))
            .fetchOne();
    }

    private List<SearchProductDto> getSearchProductIds(String keyword, Pageable pageable,
        QCategoryProduct categoryProduct, QProduct product, QCategory category) {

        return jpaQueryFactory
            .select(new QSearchResponse_SearchProductDto(product.id))
            .from(categoryProduct)
            .innerJoin(categoryProduct.product, product)
            .innerJoin(categoryProduct.category, category)
            .where(validateContainsKeyword(keyword, product, category))
            .orderBy(product.id.desc())
            .distinct()
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

    private BooleanExpression validateContainsKeyword(
        String keyword, QProduct product, QCategory category) {
        return product.productName.containsIgnoreCase(keyword)
            .or(category.categoryName.containsIgnoreCase(keyword))
            .or(product.brand.brandName.containsIgnoreCase(keyword));
    }

    @Override
    public List<SearchTextDto> searchAllKeywordV1(String keyword) {
        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;
        List<String> searchResults = getSearchResultsV1(keyword, categoryProduct, product,
            category);

        return searchResults.stream()
            .map(SearchTextDto::new)
            .distinct()
            .toList();
    }

    private List<String> getSearchResultsV1(String keyword, QCategoryProduct categoryProduct,
        QProduct product, QCategory category) {
        return jpaQueryFactory
            .select(
                new CaseBuilder()
                    .when(category.categoryName.containsIgnoreCase(keyword))
                    .then(category.categoryName)
                    .when(product.productName.containsIgnoreCase(keyword))
                    .then(product.productName)
                    .otherwise((String) null)
            )
            .from(categoryProduct)
            .innerJoin(categoryProduct.product, product)
            .innerJoin(categoryProduct.category, category)
            .where(category.categoryName.containsIgnoreCase(keyword)
                .or(product.productName.containsIgnoreCase(keyword)))
            .distinct()
            .fetch();
    }

    @Override
    public List<SearchTextDto> searchAllKeywordV2(String keyword) {
        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;
        List<String> searchResults = getSearchResultsV2(keyword, categoryProduct, product,
            category);

        return searchResults.stream()
            .map(SearchTextDto::new)
            .distinct()
            .toList();
    }

    private List<String> getSearchResultsV2(String keyword, QCategoryProduct categoryProduct,
        QProduct product, QCategory category) {
        return jpaQueryFactory
            .select(
                new CaseBuilder()
                    .when(category.categoryName.containsIgnoreCase(keyword))
                    .then(category.categoryName)
                    .when(product.productName.containsIgnoreCase(keyword))
                    .then(product.productName)
                    .otherwise((String) null)
            )
            .from(categoryProduct)
            .innerJoin(categoryProduct.product, product)
            .innerJoin(categoryProduct.category, category)
            .where(category.categoryName.containsIgnoreCase(keyword)
                .or(product.productName.containsIgnoreCase(keyword)))
            .distinct()
            .limit(SEARCH_SIZE)
            .fetch();
    }
}
