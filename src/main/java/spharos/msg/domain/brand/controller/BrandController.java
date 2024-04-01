package spharos.msg.domain.brand.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.brand.service.BrandService;
import spharos.msg.global.api.ApiResponse;

@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ApiResponse<?> getBrands(){
        return brandService.getBrands();
    }
}
