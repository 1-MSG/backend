package spharos.msg.domain.category.converter;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDto;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDtos;
import spharos.msg.domain.category.dto.CategoryResponse.SubCategory;
import spharos.msg.domain.category.entity.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryConverter {

    public static CategoryDto toDto(List<SubCategory> categories) {
        return CategoryDto
            .builder()
            .subCategories(categories)
            .build();
    }

    public static CategoryDto toDto(Category parent, List<SubCategory> categories) {
        return CategoryDto
            .builder()
            .parentId(parent.getId())
            .parentName(parent.getCategoryName())
            .subCategories(categories)
            .build();
    }

    public static CategoryProductDtos toDto(Page<CategoryProductDto> categoryProducts) {
        return CategoryProductDtos
            .builder()
            .categoryProducts(categoryProducts.getContent())
            .isLast(categoryProducts.isLast())
            .build();
    }
}
