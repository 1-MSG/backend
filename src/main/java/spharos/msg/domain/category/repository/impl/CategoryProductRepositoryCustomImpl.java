package spharos.msg.domain.category.repository.impl;

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
import spharos.msg.domain.category.dto.QCategoryResponse_CategoryProductDto;
import spharos.msg.domain.category.dto.QCategoryResponse_SubCategory;
import spharos.msg.domain.category.entity.QCategory;
import spharos.msg.domain.category.entity.QCategoryProduct;
import spharos.msg.domain.category.repository.CategoryProductRepositoryCustom;
import spharos.msg.domain.product.entity.QProduct;

@Component
@RequiredArgsConstructor
public class CategoryProductRepositoryCustomImpl implements CategoryProductRepositoryCustom {

    private static final String NO_IMAGE = "NONE";
    private static final String ALL_CATEGORY_SELECT_NAME = "상품 전체보기";

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SubCategory> findCategoriesByParentId(Long parentId) {
        QCategory category = QCategory.category;
        List<Long> categoryIds = findIds(parentId, category);
        categoryIds.add(0, parentId);

        return jpaQueryFactory
            .select(new QCategoryResponse_SubCategory(
                category.id,
                new CaseBuilder()
                    .when(category.id.eq(parentId))
                    .then(ALL_CATEGORY_SELECT_NAME)
                    .otherwise(category.categoryName),
                tryGetCategoryImage(category)))
            .from(category)
            .where(category.id.in(categoryIds))
            .fetch();
    }

    @Override
    public List<SubCategory> findCategoriesByLevel(int categoryLevel) {
        QCategory category = QCategory.category;
        return jpaQueryFactory
            .select(toSubCategoryDto(null, category))
            .from(category)
            .where(category.categoryLevel.eq(categoryLevel))
            .distinct()
            .fetch();
    }

    private List<Long> findIds(Long parentId, QCategory category) {
        return jpaQueryFactory
            .select(category.id)
            .from(category)
            .where(validateParentAndLevel(parentId, category))
            .fetch();
    }

    private QCategoryResponse_SubCategory toSubCategoryDto(Long parentId, QCategory category) {
        BooleanExpression isNotNullAndSameWithParentId = (parentId != null) ?
            category.id.eq(parentId) :
            null;

        return new QCategoryResponse_SubCategory(
            category.id,
            new CaseBuilder()
                .when(isNotNullAndSameWithParentId)
                .then(ALL_CATEGORY_SELECT_NAME)
                .otherwise(category.categoryName),
            tryGetCategoryImage(category)
        );
    }

    private StringExpression tryGetCategoryImage(QCategory category) {
        return new CaseBuilder()
            .when(category.categoryImage.isNotEmpty())
            .then(category.categoryImage)
            .otherwise(NO_IMAGE);
    }

    private BooleanExpression validateParentAndLevel(Long parentId, QCategory category) {
        return category.parent.id.eq(parentId)
            .and(category.categoryLevel
                .eq(category.parent.categoryLevel.add(1)));
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

    private List<Long> getCategoryIds(Long categoryId, QCategory category) {
        return jpaQueryFactory
            .select(category.id)
            .from(category)
            .where(validateChildCategory(category, categoryId))
            .distinct()
            .fetch();
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
            .select(new QCategoryResponse_CategoryProductDto(product.id))
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
