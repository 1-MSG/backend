package spharos.msg.domain.product.controller;

import static spharos.msg.global.api.code.status.SuccessStatus.PRODUCT_BEST_SUCCESS;
import static spharos.msg.global.api.code.status.SuccessStatus.PRODUCT_INFO_SUCCESS;
import static spharos.msg.global.api.code.status.SuccessStatus.PRODUCT_RANDOM_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.product.dto.ProductResponse;
import spharos.msg.domain.product.service.ProductServiceV2;
import spharos.msg.global.api.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v2/")
@Tag(name = "Product V2", description = "상품 API")
@Slf4j
public class ProductControllerV2 {

    private final ProductServiceV2 productService;

    @Operation(summary = "상품 기본정보 조회",
        description = "개별 상품에 대한 기본 정보를 조회합니다")
    @GetMapping("/product/{productId}")
    public ApiResponse<ProductResponse.ProductInfoDto> getProductDetails(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductInfo(productId));
    }

    @Operation(summary = "상품 썸네일 조회",
        description = "특정 상품에 대한 상품 이미지(썸네일)을 반환합니다")
    @GetMapping("/product/{productId}/image")
    public ApiResponse<ProductResponse.ProductImageDto> getProductImage(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductImage(productId));
    }

    @Operation(summary = "상품 이미지 전체 조회",
        description = "특정 상품에 대한 상품 이미지 리스트를 반환합니다")
    @GetMapping("/product/{productId}/images")
    public ApiResponse<List<ProductResponse.ProductImageDto>> getProductImages(@PathVariable("productId") Long productId) {
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
    public ApiResponse<ProductResponse.ProductCategoryDto> getProductCategory(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductCategory(productId));
    }

    @Operation(summary = "여러 상품 정보 조회",
        description = "상품들에 대한 상세 정보를 조회합니다")
    @GetMapping("/products")
    public ApiResponse<List<ProductResponse.ProductInfoAdminDto>> getProductsDetails(
        @RequestParam(value = "productIds", defaultValue = "1") List<Long> productIds
    ) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS,productService.getProductsDetails(productIds));
    }

    @Operation(summary = "베스트 상품 목록 조회",
        description = "판매수량을 내림차순하여 상품들을 반환합니다. 판매수량과 상품Id를 cursor(기준점)삼아 페이지네이션합니다")
    @GetMapping("/ranking")
    public ApiResponse<ProductResponse.BestProductsDto> getRankingProducts(
        @RequestParam(value = "size",defaultValue = "10") Integer size,
        @RequestParam(value = "page",defaultValue = "0") Integer page,
        @RequestParam(value = "cursorTotalSellCount",defaultValue = "999999") Long cursorTotalSellCount,
        @RequestParam(value = "cursorId",defaultValue = "1") Long cursorId
    ) {

        Pageable pageable = PageRequest.of(page,size);
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getRankingProducts(pageable,cursorTotalSellCount,cursorId));
    }

    @Operation(summary = "랜덤 상품 목록 조회",
        description = "랜덤 섹션의 상품 목록")
    @GetMapping("/random")
    public ApiResponse<List<ProductResponse.ProductIdDto>> getRandomProducts() {
        return ApiResponse.of(PRODUCT_RANDOM_SUCCESS, productService.getRandomProducts());
    }
  
    @Operation(summary = "베스트11 조회",
        description = "베스트 상품 11 객체를 반환합니다")
    @GetMapping("/ranking11")
    public ApiResponse<List<ProductResponse.Best11Dto>> getBest11Products(
    ) {
        return ApiResponse.of(PRODUCT_BEST_SUCCESS, productService.getBest11Products());
    }

    @Operation(summary = "상품 배송정보 조회",
        description = "특정 상품에 대한 배송정보(기본배송비, 무료배송 최소금액)를 반환합니다")
    @GetMapping("/product/{productId}/deliveryinfo")
    public ApiResponse<ProductResponse.ProductDeliveryDto> getProductDeliveryInfo(@PathVariable("productId") Long productId) {
        return ApiResponse.of(PRODUCT_INFO_SUCCESS, productService.getProductDeliveryInfo(productId));
    }
}