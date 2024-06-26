package spharos.msg.domain.admin.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spharos.msg.domain.admin.dto.AdminResponseDto;
import spharos.msg.domain.admin.dto.AdminResponseDto.MonthlySignupCount;
import spharos.msg.domain.admin.service.Impl.CountUserServiceImplV1;
import spharos.msg.global.api.ApiResponse;
import spharos.msg.global.api.code.status.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
@Tag(name = "Admin Users V1", description = "어드민 관련 페이지")
public class CountUserControllerV1 {

    private final CountUserServiceImplV1 countUserService;

    //전체회원 정보 조회
    //Pageable
    @Operation(summary = "전체 회원 정보 조회 API", description = "전체 회원에 대한 정보를 반환합니다.")
    @GetMapping("/search-all")
    private ApiResponse<List<AdminResponseDto.SearchAllInfo>> SearchAllUsersApi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        return ApiResponse.of(
                SuccessStatus.SEARCH_USERS_INFO_SUCCESS,
                countUserService.SearchUsersInfo(pageable));
    }

    @Operation(summary = "현재 접속자 수 조회 API", description = "현재 접속자 수를 반환합니다.")
    @GetMapping("/connect-user")
    private ApiResponse<AdminResponseDto.ConnectCount> ConnectCountApi() {
        return ApiResponse.of(SuccessStatus.COUNT_CONNECT_USERS_SUCCESS,
                countUserService.countConnectUser());
    }

    @Operation(summary = "전체 회원 수 조회 API", description = "전체 회원 수를 반환합니다.")
    @GetMapping("/count-user")
    private ApiResponse<AdminResponseDto.UsersCount> UsersCountApi() {
        return ApiResponse.of(SuccessStatus.COUNT_TOTAL_USERS_SUCCESS,
                countUserService.usersCount());
    }

    @Operation(summary = "오늘 가입자 수 조회 API", description = "오늘 가입한 회원 수를 반환합니다.")
    @GetMapping("/count-today-user")
    private ApiResponse<AdminResponseDto.UsersCount> todaySignupCountApi() {
        return ApiResponse.of(SuccessStatus.TODAY_SIGNUP_COUNT_SUCCESS,
                countUserService.todaySignupCount());
    }

    @Operation(summary = "전체 탈퇴 회원 수 조회 API", description = "전체 탈퇴 회원의 수를 반환합니다.")
    @GetMapping("/count-secession-user")
    private ApiResponse<AdminResponseDto.SecessionCount> UsersSecessionCountApi() {
        return ApiResponse.of(SuccessStatus.COUNT_SESSION_USERS_SUCCESS,
                countUserService.secessionCount());
    }


    @Operation(summary = "월별 가입자 회원 수 조회 API", description = "월별 가입자 회원의 수를 반환합니다.")
    @GetMapping("/count-monthly-assign")
    private ApiResponse<List<List<AdminResponseDto.MonthlySignupCount>>> MonthlyAssignCountApi() {
        List<List<MonthlySignupCount>> result = countUserService.monthSignupCount();
        return ApiResponse.of(SuccessStatus.COUNT_MONTHLY_ASSIGN_SUCCESS, result);
    }

    @Operation(summary = "회원 정보 조회 API", description = "회원 이름을 받아 회원 정보 리스트를 반환합니다.")
    @GetMapping("/users")
    private ApiResponse<List<AdminResponseDto.SearchAllInfo>> MonthlyAssignCountApi(
            @RequestParam String userName) {
        return ApiResponse.of(SuccessStatus.SEARCH_USERS_INFO_BY_USERNAME_SUCCESS,
                countUserService.SearchUsersInfoByUserName(userName));
    }
}
