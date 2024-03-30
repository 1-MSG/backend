package spharos.msg.domain.category.repository;

import java.util.List;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;

public interface CategoryProductRepositoryCustom {

    List<CategoryDto> findCategoriesByParentId(Long parentId);

    CategoryProductDto findCategoryProductsById(Long categoryId);

}
