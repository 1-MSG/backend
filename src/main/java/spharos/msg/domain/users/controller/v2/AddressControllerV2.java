package spharos.msg.domain.users.controller.v2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.users.dto.request.AddressRequest;
import spharos.msg.domain.users.dto.response.AddressResponse;
import spharos.msg.domain.users.service.AddressService;
import spharos.msg.domain.users.service.impl.v2.AddressServiceImplV2;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/address")
@Tag(name = "Address V2", description = "배송지 관련 API")
public class AddressControllerV2 {

    private final AddressServiceImplV2 addressService;

    @Operation(summary = "배송지 추가", description = "해당 회원의 배송지를 추가 합니다.")
    @PostMapping("/{userId}")
    public ApiResponse<Void> addAddress(
            @RequestBody AddressRequest.AddAddressDto dto,
            @PathVariable(name = "userId") Long userId
    ) {
        addressService.createAddress(dto, userId);
        return ApiResponse.of(SuccessStatus.DELIVERY_ADDRESS_ADD_SUCCESS, null);
    }

    @Operation(summary = "배송지 정보 조회", description = "해당 회원의 모든 배송지를 조회합니다.")
    @GetMapping("/{userId}")
    public ApiResponse<List<AddressResponse.SearchAddressDto>> searchAllAddress(
            @PathVariable(name = "userId") Long userId
    ) {
        return ApiResponse.of(SuccessStatus.SEARCH_ALL_ADDRESS_SUCCESS, addressService.searchAllAddress(userId));
    }

    @Operation(summary = "배송지 삭제", description = "해당 회원의 선택된 배송지를 삭제합니다.")
    @DeleteMapping("/{userId}/{addressId}")
    public ApiResponse<Void> deleteAddress(
            @PathVariable("userId") Long userId,
            @PathVariable("addressId") Long addressId
    ) {
        addressService.deleteAddress(userId, addressId);
        return ApiResponse.of(SuccessStatus.DELETE_ADDRESS_SUCCESS, null);
    }
}
