package backend.core.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    LOGIN_FAILED(BAD_REQUEST, "로그인에 실패했습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_USER(UNAUTHORIZED, "인증되지 않은 사용자입니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    RATING_INFO_NOT_FOUND(NOT_FOUND, "평가 내용을 찾을 수 없습니다"),
    RATING_NOT_FOUND(NOT_FOUND, "사용자 평가를 찾을 수 없습니다"),
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }
}
