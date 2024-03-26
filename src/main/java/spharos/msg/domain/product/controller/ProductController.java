package spharos.msg.domain.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.product.dto.ProductDetailInfoDto;
import spharos.msg.domain.product.service.ProductService;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.ErrorStatus;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Tag(name = "Product", description = "상품 관련 API")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final String FIRST_STATE = "HOME";
    private final String SECOND_STATE = "HOME2";

    @Operation(summary = "홈화면 상품 조회",
        description = "홈화면 3가지 섹션(뷰티/랜덤/음식)의 상품,패션카테고리 상품을 조회합니다")
    @GetMapping("/product-list")
    public ApiResponse<?> getHomeProducts(
        @RequestParam("param") String state,
        @RequestParam("index") int index
    ) {
        if (FIRST_STATE.equals(state) && index == 0) {
            return ApiResponse.onSuccess(productService.getHomeCosmeRandomFood());
        } else if (SECOND_STATE.equals(state)) {
            return ApiResponse.onSuccess(productService.getHomeFashion(index));
        }
        return ApiResponse.onFailure(ErrorStatus.PRODUCT_ERROR.getStatus(),
            ErrorStatus.PRODUCT_ERROR.getMessage(), null);
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<?> getProductDetails(@PathVariable("productId") Long product_id) {
        return productService.getProductDetail(product_id);
    }
}
