package spharos.msg.domain.category.controller;

import static spharos.msg.domain.category.dto.CategoryResponse.CategoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.category.service.CategoryService;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Tag(name = "Category", description = "카테고리 API")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 id를 통한 자식 카테고리 조회",
        description = "해당 카테고리의 바로 아래 차수의 카테고리를 조회합니다.")
    @GetMapping("/category")
    public ApiResponse<List<CategoryDto>> findCategoryAPI(
        @RequestParam("categoryId") Long categoryId) {

        return ApiResponse.of(
            SuccessStatus.CATEGORY_LIST_SUCCESS,
            categoryService.findCategoryChild(categoryId));
    }
}
