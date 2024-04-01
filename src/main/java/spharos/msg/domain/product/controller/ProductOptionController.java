package spharos.msg.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.product.service.ProductOptionService;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-option")
public class ProductOptionController {
    private final ProductOptionService productOptionService;
    @GetMapping("{productId}")
    public ApiResponse<?> getAllOptionId(
            @PathVariable Long productId
    ){
        return productOptionService.getAllOptionId(productId);
    }
}
