package spharos.msg.domain.category.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
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
    @AllArgsConstructor
    public static class SubCategory {

        private Long categoryId;
        private String categoryName;
        private String categoryImage;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class CategoryProductDtos {

        @JsonProperty("isLast")
        Boolean isLast = false;
        List<CategoryProductDto> categoryProducts;
    }

    @NoArgsConstructor
    @Getter
    @ToString
    @AllArgsConstructor
    public static class CategoryProductDto {

        Long productId;
    }
}
