package spharos.msg.domain.category.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;

public interface CategoryProductRepositoryCustom {

    List<CategoryDto> findCategoriesByParentId(Long parentId);

    List<CategoryDto> findCategoriesByLevel(int categoryLevel);

    Page<CategoryProductDto> findCategoryProductsById(Long categoryId, Pageable pageable);

}
