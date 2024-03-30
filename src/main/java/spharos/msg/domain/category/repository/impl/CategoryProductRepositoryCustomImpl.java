package spharos.msg.domain.category.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;
import spharos.msg.domain.category.entity.QCategory;
import spharos.msg.domain.category.repository.CategoryProductRepositoryCustom;

@Component
@RequiredArgsConstructor
public class CategoryProductRepositoryCustomImpl implements CategoryProductRepositoryCustom {

    private static final String NO_IMAGE = "NONE";
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CategoryDto> findCategoriesByParentId(Long parentId) {
        QCategory category = QCategory.category;
        return jpaQueryFactory
            .select(Projections.constructor(CategoryDto.class,
                category.id,
                category.categoryName,
                tryGetCategoryImage(category)))
            .from(category)
            .where(validateCorrectParentAndLevel(parentId, category))
            .fetch();
    }

    private BooleanExpression validateCorrectParentAndLevel(Long parentId, QCategory category) {
        return category.parent.id.eq(parentId)
            .and(category.productCategoryLevel
                .eq(category.parent.productCategoryLevel.add(1)));
    }

    private StringExpression tryGetCategoryImage(QCategory category) {
        return new CaseBuilder()
            .when(category.categoryImage.isNotEmpty())
            .then(category.categoryImage)
            .otherwise(NO_IMAGE);
    }

    @Override
    public CategoryProductDto findCategoryProductsById(Long categoryId) {
        return null;
    }
}
