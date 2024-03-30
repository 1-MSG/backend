package spharos.msg.domain.category.dto;


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

        private Long categoryId;
        private String categoryName;
        private String categoryImage;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class CategoryProductDto {

        @JsonProperty("isLast")
        Boolean isLast = false;
        List<CategoryProductDetail> categoryProducts;
    }

    @NoArgsConstructor
    @Getter
    @ToString
    @AllArgsConstructor
    public static class CategoryProductDetail {

        Long productId;
    }
}
