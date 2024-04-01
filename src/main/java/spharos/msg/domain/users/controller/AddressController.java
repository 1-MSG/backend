package spharos.msg.domain.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.msg.domain.users.dto.request.AddressRequestDto;
import spharos.msg.domain.users.dto.response.SearchAddressOutDto;
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
    @PostMapping("/{userId}")
    public ApiResponse<?> addAddress(
            @RequestBody AddressRequestDto addressRequestDto,
            @PathVariable(name = "userId") Long userId
    ) {
        addressService.createAddress(addressRequestDto, userId);
        return ApiResponse.of(SuccessStatus.DELIVERY_ADDRESS_ADD_SUCCESS, null);
    }

    @Operation(summary = "배송지 정보 조회", description = "해당 회원의 모든 배송지를 조회합니다.")
    @GetMapping("/{userId}")
    public ApiResponse<List<SearchAddressOutDto>> searchAllAddress(
            @PathVariable(name = "userId") Long userId
    ) {
        List<SearchAddressOutDto> result = addressService.searchAllAddress(userId);
        return ApiResponse.of(SuccessStatus.SEARCH_ALL_ADDRESS_SUCCESS, result);
    }

    @Operation(summary = "배송지 삭제", description = "해당 회원의 선택된 배송지를 삭제합니다.")
    @DeleteMapping("/{userId}/{addressId}")
    public ApiResponse<?> deleteAddress(
            @PathVariable("userId") Long userId,
            @PathVariable("addressId") Long addressId
    ) {
        addressService.deleteAddress(userId, addressId);
        return ApiResponse.of(SuccessStatus.DELETE_ADDRESS_SUCCESS, null);
    }
}
