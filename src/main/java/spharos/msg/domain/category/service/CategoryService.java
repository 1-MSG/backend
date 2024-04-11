package spharos.msg.domain.category.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDtos;
import spharos.msg.domain.category.dto.CategoryResponse.SubCategory;
import spharos.msg.domain.category.entity.Category;

@Service
public interface CategoryService {
    CategoryDto findCategories(Long parentId);

    List<SubCategory> findCategoriesByLevel(int level);

    CategoryProductDtos findCategoryProducts(Long categoryId, Pageable pageable);

    CategoryDto createCategoryDto(Category currentCategory, List<SubCategory> categories);
}
