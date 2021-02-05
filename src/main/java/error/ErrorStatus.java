package error;

import exception.DuplicateException;
import exception.NotExistException;
import exception.NotFoundException;
import exception.UnauthorizedException;
import webserver.HttpStatus;

import java.io.IOException;
import java.util.Arrays;

public enum ErrorStatus {
    DUPLICATE(HttpStatus.BAD_REQUEST, DuplicateException.class),
    NOT_EXIST(HttpStatus.BAD_REQUEST, NotExistException.class),
    NOT_FOUND(HttpStatus.NOT_FOUND, NotFoundException.class),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, UnauthorizedException.class),
    IO(HttpStatus.INTERNAL_SERVER_ERROR, IOException.class),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, IllegalArgumentException.class),
    ILLEGAL_STATE(HttpStatus.BAD_REQUEST, IllegalStateException.class),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, Exception.class)
    ;

    private final HttpStatus httpStatus;
    private final Class<? extends Exception> exception;

    ErrorStatus(HttpStatus httpStatus, Class<? extends Exception> exception) {
        this.httpStatus = httpStatus;
        this.exception = exception;
    }

    public static ErrorStatus from(Exception exception) {
        return Arrays.stream(ErrorStatus.values())
                .filter(errorStatus -> errorStatus.exception.isInstance(exception))
                .findAny()
                .orElseThrow(() -> new NotExistException("일치하는 에러 타입이 없습니다."));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
