package backend.core.global.error.exception;

import backend.core.basket.exception.BasketNotFoundException;
import backend.core.global.error.exception.group.*;
import backend.core.item.exception.ItemExistException;
import backend.core.item.exception.ItemNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    LOGIN_FAILED(BAD_REQUEST, "이메일 또는 비밀번호가 틀렸습니다", SignInFailedException.class),
    EXIST_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다", ExistEmailException.class),
    EXIST_NICKNAME(BAD_REQUEST, "이미 존재하는 닉네임입니다", ExistNicknameException.class),
    INVALID_TOKEN(BAD_REQUEST, "유효하지 않은 토큰입니다", InvalidTokenException.class),
    EXPIRED_TOKEN(BAD_REQUEST, "토큰이 만료되었습니다", ExpiredTokenException.class),
    WRONG_TOKEN(BAD_REQUEST, "유효하지 않은 형식의 토큰입니다", WrongTokenException.class),
    ITEM_EXIST(BAD_REQUEST, "이미 존재하는 아이템입니다", ItemExistException.class),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_USER(UNAUTHORIZED, "인증되지 않은 사용자입니다", UnauthorizedUserException.class),
    UNAUTHORIZED_TOKEN(UNAUTHORIZED, "보안 컨텍스트에서 사용자 인증을 설정할 수 없습니다", UnauthorizedTokenException.class),

    /* 407 NOT_ACCEPTABLE : 허용되지 않는 접근 */
    MEMBER_NOT_ACCEPTABLE(NOT_ACCEPTABLE, "작성자만 수정할 수 있습니다", MemberNotAcceptableException.class),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    //    RATING_INFO_NOT_FOUND(NOT_FOUND, "평가 내용을 찾을 수 없습니다"),
    //    RATING_NOT_FOUND(NOT_FOUND, "사용자 평가를 찾을 수 없습니다"),
    MEMBER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다", MemberNotFoundException.class),
    CHAT_NOT_FOUND(NOT_FOUND, "채팅방을 찾을 수 없습니다", ChatNotFoundException.class),
    POST_NOT_FOUND(NOT_FOUND, "게시글을 찾을 수 없습니다", PostNotFoundException.class),
    BASKET_NOT_FOUND(NOT_FOUND, "장바구니를 찾을 수 없습니다", BasketNotFoundException.class),
    ITEM_NOT_FOUND(NOT_FOUND, "아이템을 찾을 수 없습니다", ItemNotFoundException.class),
    TAG_NOT_FOUND(NOT_FOUND, "태그를 찾을 수 없습니다", TagNotFoundException.class),
    MESSAGE_NOT_FOUND(NOT_FOUND, "메시지를 찾을 수 없습니다", MessageNotFoundException.class),
    STAFF_NOT_FOUND(NOT_FOUND, "구성원을 찾을 수 없습니다", StaffNotFoundException.class),
    TOKEN_NOT_FOUND(NOT_FOUND, "토큰을 찾을 수 없습니다", TokenNotFoundException.class),

    /* 404 NOT_FOUND : 에러 클래스를 찾을 수 없을 경우 */
    CLASS_NOT_FOUND(NOT_FOUND, "에러 클래스를 찾을 수 없습니다", NotFoundClassException.class),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final Class<? extends Exception> klass;

    ErrorCode(HttpStatus httpStatus, String message, Class<? extends Exception> klass) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.klass = klass;
    }

    public static ErrorCode findByClass(Class<? extends Exception> klass) {
        return Arrays.stream(ErrorCode.values())
                .filter(code -> code.klass.equals(klass))
                .findAny()
                .orElseThrow(() -> new NotFoundClassException());
    }
}
