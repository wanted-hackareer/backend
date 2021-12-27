package backend.core.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode
            .findByClass(this.getClass());

    private HttpStatus httpStatus;
    private String message;

    public CustomException() {
        httpStatus = errorCode.getHttpStatus();
        message = errorCode.getMessage();
    }
}
