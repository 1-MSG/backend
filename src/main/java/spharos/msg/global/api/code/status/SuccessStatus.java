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
    LOGIN_SUCCESS(HttpStatus.ACCEPTED, "USER203", "통합 로그인 성공"),
    LOGIN_SUCCESS_UNION(HttpStatus.ACCEPTED, "USER203", "통합 로그인 성공"),
    LOGIN_SUCCESS_EASY(HttpStatus.ACCEPTED, "USER203", "간편 로그인 성공"),
    ORDER_SUCCESS(HttpStatus.CREATED, "ORDER201", "상품 주문 성공"),
    ORDER_USER_SUCCESS(HttpStatus.OK, "ORDER202", "주문자 정보 조회 성공"),
    CART_PRODUCT_ADD_SUCCESS(HttpStatus.CREATED, "CART301", "장바구니 담기 성공"),
    CART_PRODUCT_GET_SUCCESS(HttpStatus.OK, "CART302", "장바구니 조회 성공"),
    CART_PRODUCT_UPDATE_SUCCESS(HttpStatus.OK, "CART303", "장바구니 수정 성공"),
    CART_PRODUCT_DELETE_SUCCESS(HttpStatus.OK, "CART304", "장바구니 삭제 성공"),
    CART_PRODUCT_OPTION_SUCCESS(HttpStatus.OK, "CART305", "장바구니 옵션 조회 성공"),
    LIKES_SUCCESS(HttpStatus.CREATED, "LIKES401", "좋아요 등록 성공"),
    LIKES_DELETE_SUCCESS(HttpStatus.CREATED, "LIKES402", "좋아요 해제 성공"),
    LIKES_LIST_GET_SUCCESS(HttpStatus.CREATED, "LIKES403", "좋아요 목록 조회 성공"),

    COUPON_LIST_GET_SUCCESS(HttpStatus.OK, "COUPON501", "다운 가능 쿠폰 목록 조회 성공"),
    COUPON_DOWNLOAD_SUCCESS(HttpStatus.OK, "COUPON502", "쿠폰 다운로드 성공"),
    COUPON_GET_USERS_SUCCESS(HttpStatus.OK, "COUPON503", "보유 쿠폰 목록 조회 성공"),
    TOKEN_REISSUE_COMPLETE(HttpStatus.ACCEPTED, "USER204", "토큰 재발급 성공");

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

