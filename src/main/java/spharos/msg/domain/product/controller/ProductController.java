package spharos.msg.domain.product.controller;

import static spharos.msg.global.api.code.status.SuccessStatus.PRODUCT_INFO_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.product.dto.ProductResponse;
import spharos.msg.domain.product.service.ProductService;
import spharos.msg.global.api.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Tag(name = "Product", description = "상품 관련 API")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 상세 조회",
        description = "개별 상품에 대한 상세 정보를 조회합니다")
    @GetMapping("/product/{productId}")
    public ApiResponse<ProductResponse.ProductInfoDto> getProductDetails(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductInfo(productId));
    }

    @Operation(summary = "상품 썸네일 조회",
        description = "특정 상품에 대한 상품 이미지(썸네일)을 반환합니다")
    @GetMapping("/product/{productId}/image")
    public ApiResponse<ProductResponse.ProductImage> getProductImage(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductImage(productId));
    }

    @Operation(summary = "상품 이미지 전체 조회",
        description = "특정 상품에 대한 상품 이미지 리스트를 반환합니다")
    @GetMapping("/product/{productId}/images")
    public ApiResponse<List<ProductResponse.ProductImage>> getProductImages(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductImages(productId));
    }

    @Operation(summary = "상품 상세 html 조회",
        description = "특정 상품에 대한 상품 상세 정보 html을 반환합니다")
    @GetMapping("/product/{productId}/detail")
    public ApiResponse<String> getProductDetail(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductDetail(productId));
    }

    @Operation(summary = "상품 카테고리 정보 조회",
        description = "특정 상품이 속한 카테고리를 반환합니다")
    @GetMapping("/product/{productId}/category")
    public ApiResponse<ProductResponse.ProductCategory> getProductCategory(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductCategory(productId));
    }

    @Operation(summary = "여러 상품 정보 조회",
        description = "상품들에 대한 상세 정보를 조회합니다")
    @GetMapping("/products")
    public ApiResponse<?> getProductsDetails(
        @RequestParam("productIds") String productIds
    ) {
        // 쉼표로 구분된 문자열을 배열로 변환
        String[] idArray = productIds.split(",");

        // 배열을 Long 형식의 리스트로 변환
        List<Long> idList = Arrays.stream(idArray)
            .map(Long::valueOf)
            .toList();

        return null;
    }
}
