package spharos.msg.global.api.code.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import spharos.msg.global.api.code.BaseCode;
import spharos.msg.global.api.dto.ReasonDto;

@Getter
@RequiredArgsConstructor
public enum SuccessStatus implements BaseCode {
    SIGN_UP_SUCCESS_UNION(HttpStatus.CREATED, "USER201", "통합 회원가입 성공"),
    SIGN_UP_SUCCESS_EASY(HttpStatus.CREATED, "USER202", "간편 회원가입 성공"),
    LOGIN_SUCCESS_UNION(HttpStatus.ACCEPTED, "USER204", "통합 로그인 성공"),
    LOGIN_SUCCESS_EASY(HttpStatus.ACCEPTED, "USER205", "간편 로그인 성공"),
    LOGOUT_SUCCESS(HttpStatus.ACCEPTED, "USER207", "로그아웃 성공"),
    EMAIL_SEND_SUCCESS(HttpStatus.ACCEPTED, "USER208", "이메일 발송 성공"),
    EMAIL_AUTH_SUCCESS(HttpStatus.ACCEPTED, "USER209", "이메일 인증 성공"),
    DUPLICATION_CHECK_SUCCESS(HttpStatus.ACCEPTED, "USER210", "사용 가능한 아이디 입니다."),
    DELIVERY_ADDRESS_ADD_SUCCESS(HttpStatus.ACCEPTED, "USER211", "배송지 추가 완료"),
    WITHDRAW_USER_SUCCESS(HttpStatus.ACCEPTED, "USER212", "회원 탈퇴 성공"),
    CHANGE_PASSWORD_SUCCESS(HttpStatus.ACCEPTED, "USER213", "비밀번호 재설정 성공"),
    SEARCH_ALL_ADDRESS_SUCCESS(HttpStatus.ACCEPTED, "USER214", "전체 배송지 조회 성공"),
    DELETE_ADDRESS_SUCCESS(HttpStatus.ACCEPTED, "USER215", "배송지 삭제 성공"),
    FIND_LOGIN_ID_SUCCESS(HttpStatus.ACCEPTED, "USER216", "아이디 찾기 성공"),
    FIND_USER_INFO_SUCCESS(HttpStatus.ACCEPTED, "USER217", "회원 정보 조회 성공"),

    SEARCH_USERS_INFO_SUCCESS(HttpStatus.ACCEPTED, "ADMIN001", "회원 정보 조회 성공"),
    COUNT_CONNECT_USERS_SUCCESS(HttpStatus.ACCEPTED, "ADMIN003", "접속자 수 조회 성공"),
    COUNT_TOTAL_USERS_SUCCESS(HttpStatus.ACCEPTED, "ADMIN004", "전체 회원 수 조회 성공"),
    TODAY_SIGNUP_COUNT_SUCCESS(HttpStatus.ACCEPTED, "ADMIN005", "오늘 가입자 수 조회 성공"),
    COUNT_SESSION_USERS_SUCCESS(HttpStatus.ACCEPTED, "ADMIN006", "전체 회원 탈퇴 수 조회 성공"),
    COUNT_MONTHLY_ASSIGN_SUCCESS(HttpStatus.ACCEPTED, "ADMIN006", "월별 가입자 수 조회 성공"),
    TOTAL_SALES_PRICE_SUCCESS(HttpStatus.ACCEPTED, "ADMIN007", "총 매출액 조회 성공"),
    TOTAL_DELIVERY_FEE_SUCCESS(HttpStatus.ACCEPTED, "ADMIN008", "총 배송비 조회 성공"),


    ORDER_SUCCESS(HttpStatus.CREATED, "ORDER201", "상품 주문 성공"),
    ORDER_USER_SUCCESS(HttpStatus.OK, "ORDER202", "주문자 정보 조회 성공"),
    ORDER_HISTORY_SUCCESS(HttpStatus.OK, "ORDER203", "구매 내역 리스트 조회 성공"),
    CART_PRODUCT_ADD_SUCCESS(HttpStatus.CREATED, "CART201", "장바구니 담기 성공"),
    CART_PRODUCT_GET_SUCCESS(HttpStatus.OK, "CART200", "장바구니 조회 성공"),
    CART_PRODUCT_UPDATE_SUCCESS(HttpStatus.OK, "CART202", "장바구니 수정 성공"),
    CART_PRODUCT_DELETE_SUCCESS(HttpStatus.OK, "CART203", "장바구니 삭제 성공"),
    CART_PRODUCT_OPTION_SUCCESS(HttpStatus.OK, "CART204", "장바구니 옵션 조회 성공"),

    LIKES_SUCCESS(HttpStatus.CREATED, "LIKES201", "좋아요 등록 성공"),
    LIKES_DELETE_SUCCESS(HttpStatus.CREATED, "LIKES202", "좋아요 해제 성공"),
    LIKES_LIST_GET_SUCCESS(HttpStatus.CREATED, "LIKES200", "좋아요 목록 조회 성공"),
    LIKES_GET_SUCCESS(HttpStatus.OK, "LIKES203", "좋아요 유무 조회 성공"),

    COUPON_LIST_GET_SUCCESS(HttpStatus.OK, "COUPON200", "다운 가능 쿠폰 목록 조회 성공"),
    COUPON_DOWNLOAD_SUCCESS(HttpStatus.OK, "COUPON201", "쿠폰 다운로드 성공"),
    COUPON_GET_USERS_SUCCESS(HttpStatus.OK, "COUPON202", "보유 쿠폰 목록 조회 성공"),
    TOKEN_REISSUE_COMPLETE(HttpStatus.ACCEPTED, "USER204", "토큰 재발급 성공"),

    PRODUCT_DETAIL_READ_SUCCESS(HttpStatus.OK, "PRODUCT200", "상품 상세 조회 성공"),
    PRODUCT_INFO_SUCCESS(HttpStatus.OK, "PRODUCT200", "상품 정보 조회 성공"),
    PRODUCT_BEST_SUCCESS(HttpStatus.OK, "PRODUCT200", "베스트 상품 조회 성공"),

    REVIEW_SAVE_SUCCESS(HttpStatus.CREATED, "REVIEW200", "상품 리뷰 작성 성공"),
    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "REVIEW200", "상품 리뷰 수정 성공"),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "REVIEW200", "상품 리뷰 삭제 성공"),
    REVIEW_READ_SUCCESS(HttpStatus.OK, "REVIEW200", "상품 리뷰 조회 성공"),

    SEARCH_RESULT_SUCCESS(HttpStatus.OK, "SEARCH200", "검색 결과 조회 성공"),
    SEARCH_INPUT_SUCCESS(HttpStatus.OK, "SEARCH201", "키워드 조회 성공"),

    CATEGORY_LIST_SUCCESS(HttpStatus.OK, "CATEGORY200", "카테고리 목록 조회 성공"),
    CATEGORY_PRODUCT_SUCCESS(HttpStatus.OK, "CATEGORY201", "카테고리별 상품 조회 성공"),

    BUNDLE_READ_SUCCESS(HttpStatus.OK, "BUNDLE200", "특가 목록 조회 성공"),

    OPTION_TYPE_SUCCESS(HttpStatus.OK, "OPTION200", "옵션 타입 조회 성공"),
    OPTION_ID_SUCCESS(HttpStatus.OK, "OPTION202", "옵션 ID 조회 성공"),
    OPTION_DETAIL_SUCCESS(HttpStatus.OK, "OPTION203", "옵션 상세 조회 성공"),

    BRAND_GET_SUCCESS(HttpStatus.OK, "BRAND200", "전체 브랜드 조회 성공"),
    OPTION_FIRST_SUCCESS(HttpStatus.OK, "OPTION204", "옵션 첫번째 항목 리스트 조회 성공");

    private final HttpStatus httpStatus;
    private final String status;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
            .message(message)
            .status(status)
            .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
            .message(message)
            .status(status)
            .httpStatus(httpStatus)
            .build();

    }
}

