package spharos.msg.domain.category.service;

import static spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDtos;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;
import spharos.msg.domain.category.repository.CategoryProductRepository;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.CategoryException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryProductRepository categoryProductRepository;

    public List<CategoryDto> findCategoryChild(Long parentId) {
        List<CategoryDto> categories = categoryProductRepository.findCategoriesByParentId(parentId);
        if (categories.isEmpty()) {
            throw new CategoryException(ErrorStatus.CATEGORY_NOT_FOUND);
        }
        return categories;
    }

    public CategoryProductDtos findCategoryProducts(Long categoryId, Pageable pageable) {
        Page<CategoryProductDto> findProducts = categoryProductRepository
            .findCategoryProductsById(categoryId, pageable);

        return CategoryProductDtos
            .builder()
            .categoryProducts(findProducts.getContent())
            .isLast(findProducts.isLast())
            .build();
    }
}
