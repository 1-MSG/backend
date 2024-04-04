package spharos.msg.domain.category.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;
import spharos.msg.domain.category.dto.CategoryResponse.SubCategory;
import spharos.msg.domain.category.entity.QCategory;
import spharos.msg.domain.category.entity.QCategoryProduct;
import spharos.msg.domain.category.repository.CategoryProductRepositoryCustom;
import spharos.msg.domain.product.entity.QProduct;

@Component
@RequiredArgsConstructor
public class CategoryProductRepositoryCustomImpl implements CategoryProductRepositoryCustom {

    private static final String NO_IMAGE = "NONE";

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SubCategory> findCategoriesByParentId(Long parentId) {
        QCategory category = QCategory.category;
        List<Long> categoryIds = findIds(parentId, category);
        categoryIds.add(0, parentId);

        return jpaQueryFactory
            .select(Projections.constructor(SubCategory.class,
                category.id,
                new CaseBuilder()
                    .when(category.id.eq(parentId))
                    .then("상품 전체보기")
                    .otherwise(category.categoryName),
                tryGetCategoryImage(category)))
            .from(category)
            .where(category.id.in(categoryIds))
            .fetch();
    }

    private List<Long> findIds(Long parentId, QCategory category) {
        return jpaQueryFactory
            .select(category.id)
            .from(category)
            .where(validateParentAndLevel(parentId, category))
            .fetch();
    }

    @Override
    public List<SubCategory> findCategoriesByLevel(int categoryLevel) {
        QCategory category = QCategory.category;
        return jpaQueryFactory
            .select(Projections.constructor(SubCategory.class,
                category.id,
                category.categoryName,
                tryGetCategoryImage(category)))
            .from(category)
            .where(category.productCategoryLevel.eq(categoryLevel))
            .distinct()
            .fetch();
    }

    @Override
    public Page<CategoryProductDto> findCategoryProductsById(Long categoryId, Pageable pageable) {
        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
        QCategory category = QCategory.category;
        QProduct product = QProduct.product;

        List<Long> categoryIds = getCategoryIds(categoryId, category);
        Long total = getTotal(categoryProduct, category, categoryId);
        List<CategoryProductDto> categoryProductDtos = getCategoryProductDto(
            pageable, categoryProduct, category, product, categoryIds);

        return new PageImpl<>(categoryProductDtos, pageable, total);
    }

    private StringExpression tryGetCategoryImage(QCategory category) {
        return new CaseBuilder()
            .when(category.categoryImage.isNotEmpty())
            .then(category.categoryImage)
            .otherwise(NO_IMAGE);
    }

    private List<Long> getCategoryIds(Long categoryId, QCategory category) {
        return jpaQueryFactory
            .select(category.id)
            .from(category)
            .where(validateChildCategory(category, categoryId))
            .distinct()
            .fetch();
    }

    private BooleanExpression validateParentAndLevel(Long parentId, QCategory category) {
        return category.parent.id.eq(parentId)
            .and(category.productCategoryLevel
                .eq(category.parent.productCategoryLevel.add(1)));
    }

    private Long getTotal(QCategoryProduct categoryProduct, QCategory category, Long categoryId) {
        return jpaQueryFactory
            .select(categoryProduct.countDistinct())
            .from(categoryProduct)
            .innerJoin(categoryProduct.category, category)
            .where(validateChildCategory(category, categoryId))
            .fetchOne();
    }

    private BooleanExpression validateChildCategory(QCategory category, Long categoryId) {
        return category.parent.parent.id.eq(categoryId)
            .or(category.parent.id.eq(categoryId))
            .or(category.id.eq(categoryId));
    }

    private List<CategoryProductDto> getCategoryProductDto(Pageable pageable,
        QCategoryProduct categoryProduct, QCategory category, QProduct product,
        List<Long> categoryIds) {
        return jpaQueryFactory
            .select(Projections.constructor(CategoryProductDto.class, product.id))
            .from(categoryProduct)
            .innerJoin(categoryProduct.product, product)
            .innerJoin(categoryProduct.category, category)
            .where(categoryProduct.category.id.in(categoryIds))
            .orderBy(product.id.desc())
            .distinct()
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}
