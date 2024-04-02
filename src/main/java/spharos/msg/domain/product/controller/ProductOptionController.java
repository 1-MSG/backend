package spharos.msg.domain.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.product.service.ProductOptionService;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "Options", description = "옵션 관련 API")
@RequestMapping("/api/v1/product-option")
public class ProductOptionController {
    private final ProductOptionService productOptionService;
    @Operation(summary = "상품 옵션 ID 조회",
            description = "상품의 모든 옵션 ID를 조회합니다.")
    @GetMapping("{productId}")
    public ApiResponse<?> getAllOptionId(
            @PathVariable Long productId
    ){
        return productOptionService.getAllOptionId(productId);
    }
}
