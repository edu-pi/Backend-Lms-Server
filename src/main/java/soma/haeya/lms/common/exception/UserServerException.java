package soma.haeya.lms.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserServerException extends RuntimeException {

    private final HttpStatus httpStatus;

    public UserServerException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
