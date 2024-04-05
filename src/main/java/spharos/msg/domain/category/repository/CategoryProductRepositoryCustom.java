package spharos.msg.domain.category.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;
import spharos.msg.domain.category.dto.CategoryResponse.SubCategory;

public interface CategoryProductRepositoryCustom {

    List<SubCategory> findCategoriesByParentId(Long parentId);

    List<SubCategory> findCategoriesByLevel(int categoryLevel);

    Page<CategoryProductDto> findCategoryProductsById(Long categoryId, Pageable pageable);

}
