package spharos.msg.domain.likes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.likes.dto.IsLikesDto;
import spharos.msg.domain.likes.service.LikesService;
import spharos.msg.global.api.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
@Tag(name = "Likes", description = "좋아요 API")
public class LikesController {
    private final LikesService likesService;

    @Operation(summary = "상품 좋아요 등록",
            description = "상품에 좋아요를 등록합니다.")
    @PostMapping("/{productId}")
    private ApiResponse<Void> likeProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return likesService.likeProduct(productId, userDetails.getUsername());
    }
    @Operation(summary = "상품 좋아요 해제",
            description = "상품에 등록된 좋아요를 해제합니다.")
    @DeleteMapping("/{productId}")
    private ApiResponse<Void> deleteLikeProduct(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return likesService.deleteLikeProduct(productId, userDetails.getUsername());
    }
    @Operation(summary = "좋아요한 상품 목록 조회",
            description = "좋아요 등록된 상품들을 조회합니다.")
    @GetMapping
    private ApiResponse<List<Long>> getProductLikeList(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return likesService.getProductLikeList(userDetails.getUsername());
    }

    @Operation(summary = "상품별 좋아요 조회",
            description = "각 상품의 좋아요 여부를 조회합니다.")
    @GetMapping("/{productId}")
    private ApiResponse<IsLikesDto> getProductLike(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId
    ) {
        return likesService.getProductLike(userDetails.getUsername(),productId);
    }
}