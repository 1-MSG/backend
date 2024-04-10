package spharos.msg.domain.bundle.controller;

import static spharos.msg.global.api.code.status.SuccessStatus.BUNDLE_READ_SUCCESS;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.bundle.dto.BundleResponse;
import spharos.msg.domain.bundle.dto.BundleResponse.BundleDto;
import spharos.msg.domain.bundle.service.BundleService;
import spharos.msg.global.api.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Bundle", description = "특가 관련 api")
public class BundleController {

    private final BundleService bundleService;

    @Operation(summary = "특가 목록 조회",
        description = "특가 탭에 표시되는 특가 상품 목록을 조회합니다(페이지는 0부터 시작)")
    @GetMapping("/bundles")
    public ApiResponse<BundleResponse.BundlesDto> getBundles(
        @RequestParam("page") int page,
        @RequestParam("size") int size
    ) {
        return ApiResponse.of(BUNDLE_READ_SUCCESS,bundleService.getBundles(page, size));
    }

    @Operation(summary = "특정 특가상품의 정보를 조회",
        description = "id와 일치하는 특가상품의 정보 및 속한 상품 배열을 반환합니다")
    @GetMapping("/bundles/{bundleId}")
    public ApiResponse<BundleDto> getBundleProducts(
        @PathVariable("bundleId") Long bundleId
    ) {
        return ApiResponse.of(BUNDLE_READ_SUCCESS,bundleService.getBundleInfo(bundleId));
    }
}
