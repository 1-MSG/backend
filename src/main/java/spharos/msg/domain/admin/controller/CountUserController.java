package spharos.msg.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.admin.dto.SearchUsersInfoRequestDto;
import spharos.msg.domain.admin.service.CountUserService;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
@Tag(name = "Admin Users", description = "어드민 관련 페이지")
public class CountUserController {

    private final CountUserService countUserService;
    //전체회원 정보 조회
    //Pageable
    @Operation(summary = "전체 회원 정보 조회 API", description = "전체 회원에 대한 정보를 반환합니다.")
    @PostMapping
    private ApiResponse<List<SearchUsersInfoRequestDto>> SearchAllUsersApi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        List<SearchUsersInfoRequestDto> result = countUserService.SearchUsersInfo(pageable);
        return ApiResponse.of(SuccessStatus.SEARCH_USERS_INFO_SUCCESS, result);
    }
}
