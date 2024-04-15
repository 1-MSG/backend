package spharos.msg.domain.category.controller;

import static spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.category.dto.CategoryResponse.CategoryProductDtos;
import spharos.msg.domain.category.dto.CategoryResponse.SubCategory;
import spharos.msg.domain.category.service.CategoryServiceV1;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Tag(name = "Category V1", description = "카테고리 API")
@Slf4j
public class CategoryControllerV1 {

    private final CategoryServiceV1 categoryService;

    @Operation(summary = "카테고리 id를 통한 자식 카테고리 조회",
        description = "해당 카테고리의 바로 아래 차수의 카테고리를 조회합니다.")
    @GetMapping("/category-child")
    public ApiResponse<CategoryDto> findCategoryAPI(
        @RequestParam("categoryId") Long categoryId) {
        return ApiResponse.of(
            SuccessStatus.CATEGORY_LIST_SUCCESS,
            categoryService.findCategories(categoryId));
    }

    @Operation(summary = "레벨(계층) 기준으로 카테고리 조회",
        description =
            "해당 레벨에 해당하는 모든 카테고리를 반환합니다. 예를 들어 대분류를 얻고 싶으면 level = 0을 설정합니다. "
                + "기본값은 level = 0 입니다.")
    @GetMapping("/category")
    public ApiResponse<List<SubCategory>> findCategoryByLevelAPI(
        @RequestParam(value = "level", defaultValue = "0") int level) {
        return ApiResponse.of(
            SuccessStatus.CATEGORY_LIST_SUCCESS,
            categoryService.findCategoriesByLevel(level));
    }


    @Operation(summary = "카테고리별 상품조회",
        description = "해당 카테고리를 가진 모든 상품을 조회합니다. 현재 productId순으로 내림차순 정렬이 기본입니다.")
    @GetMapping("/category-product")
    public ApiResponse<CategoryProductDtos> findCategoryProductAPI(
        @RequestParam("categoryId") Long categoryId,
        @PageableDefault(size = 10, page = 0) Pageable pageable) {

        return ApiResponse.of(
            SuccessStatus.CATEGORY_PRODUCT_SUCCESS,
            categoryService.findCategoryProducts(categoryId, pageable)
        );
    }
}
