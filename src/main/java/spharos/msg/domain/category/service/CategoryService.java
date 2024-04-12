package spharos.msg.domain.category.service;

import static spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDtos;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.category.converter.CategoryConverter;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;
import spharos.msg.domain.category.dto.CategoryResponse.SubCategory;
import spharos.msg.domain.category.entity.Category;
import spharos.msg.domain.category.repository.CategoryProductRepository;
import spharos.msg.domain.category.repository.CategoryRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.CategoryException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private static final int LARGE_CATEGORY_LEVEL = 0;
    
    private final CategoryProductRepository categoryProductRepository;
    private final CategoryRepository categoryRepository;

    public CategoryDto findCategories(Long parentId) {
        Category currentCategory = categoryRepository.findById(parentId)
            .orElseThrow(() -> new CategoryException(ErrorStatus.CATEGORY_NOT_FOUND));

        List<SubCategory> categories = categoryProductRepository.findCategoriesByParentId(parentId);
        return createCategoryDto(currentCategory, categories);
    }

    public List<SubCategory> findCategoriesByLevel(int level) {
        return categoryProductRepository.findCategoriesByLevel(level);
    }

    public CategoryProductDtos findCategoryProducts(Long categoryId, Pageable pageable) {
        Page<CategoryProductDto> findProducts = categoryProductRepository
            .findCategoryProductsById(categoryId, pageable);

        return CategoryConverter.toDto(findProducts);
    }

    private CategoryDto createCategoryDto(Category currentCategory, List<SubCategory> categories) {
        return currentCategory.getCategoryLevel() == LARGE_CATEGORY_LEVEL ?
            CategoryConverter.toDto(categories) :
            CategoryConverter.toDto(currentCategory, categories);
    }
}
