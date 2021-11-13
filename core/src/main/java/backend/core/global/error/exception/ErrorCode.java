package backend.core.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    LOGIN_FAILED(BAD_REQUEST, "이메일 또는 비밀번호가 틀렸습니다"),
    EXIST_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다"),
    EXIST_NICKNAME(BAD_REQUEST, "이미 존재하는 닉네임입니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_USER(UNAUTHORIZED, "인증되지 않은 사용자입니다"),

    /* 407 NOT_ACCEPTABLE : 허용되지 않는 접근 */
    MEMBER_NOT_ACCEPTABLE(NOT_ACCEPTABLE, "작성자만 수정할 수 있습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    RATING_INFO_NOT_FOUND(NOT_FOUND, "평가 내용을 찾을 수 없습니다"),
    RATING_NOT_FOUND(NOT_FOUND, "사용자 평가를 찾을 수 없습니다"),
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다"),
    CHAT_NOT_FOUND(NOT_FOUND, "채팅방을 찾을 수 없습니다"),
    POST_NOT_FOUND(NOT_FOUND, "게시글을 찾을 수 없습니다"),

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
