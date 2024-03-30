package spharos.msg.domain.category.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
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
}
