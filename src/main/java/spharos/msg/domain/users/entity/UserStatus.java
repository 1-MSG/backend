package spharos.msg.domain.users.entity;

/**
 * User 상태 확인용
 * NOT_USER : 회원 탈퇴로 soft delete 상태
 * EASY : 통합회원이자 간편회원인 상태
 * UNION : 통합 회원인 상태. (간편회원 X)
 */
public enum UserStatus {
    NOT_USER,
    EASY,
    UNION;
}
