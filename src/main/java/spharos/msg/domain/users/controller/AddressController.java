package spharos.msg.domain.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.users.dto.request.AddressRequestDto;
import spharos.msg.domain.users.dto.response.SearchAddressDto;
import spharos.msg.domain.users.service.AddressService;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
@Tag(name = "배송지", description = "배송지 관련 API")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "배송지 추가", description = "해당 회원의 배송지를 추가 합니다.")
    @PostMapping("add")
    public ApiResponse<?> addAddress(
            @RequestBody AddressRequestDto addressRequestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        addressService.createAddress(addressRequestDto, userDetails.getUsername());
        return ApiResponse.of(SuccessStatus.DELIVERY_ADDRESS_ADD_SUCCESS, null);
    }

    @Operation(summary = "배송지 정보 조회", description = "Uuid로 해당 회원의 모든 배송지를 조회합니다.")
    @GetMapping("search-all/{userId}")
    public ApiResponse<List<SearchAddressDto>> searchAllAddress(
            @RequestParam(name = "userId") Long userId
    ) {
        List<SearchAddressDto> result = addressService.searchAllAddress(userId);
        return ApiResponse.of(SuccessStatus.SEARCH_ALL_ADDRESS_SUCCESS, result);
    }
}
