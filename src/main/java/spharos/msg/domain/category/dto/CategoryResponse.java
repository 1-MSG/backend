package spharos.msg.domain.category.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

    @Builder
    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CategoryDto {

        /**
         * parentId, parentName은 Null이 아닐때만 포함
         */
        @JsonInclude(Include.NON_NULL)
        private Long parentId;
        @JsonInclude(Include.NON_NULL)
        private String parentName;
        private List<SubCategory> subCategories;
    }

    @Getter
    @ToString
    public static class SubCategory {

        private Long categoryId;
        private String categoryName;
        private String categoryImage;

        @QueryProjection
        public SubCategory(Long categoryId, String categoryName, String categoryImage) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.categoryImage = categoryImage;
        }
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CategoryProductDtos {

        @JsonProperty("isLast")
        Boolean isLast = false;
        List<CategoryProductDto> categoryProducts;
    }

    @Getter
    @ToString
    public static class CategoryProductDto {

        Long productId;

        @QueryProjection
        public CategoryProductDto(Long productId) {
            this.productId = productId;
        }
    }
}
