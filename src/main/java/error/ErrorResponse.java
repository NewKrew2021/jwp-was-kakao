package error;

import webserver.HttpStatus;

public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final String message;

    private ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static ErrorResponse from(Exception exception) {
        ErrorStatus errorStatus = ErrorStatus.from(exception);
        return new ErrorResponse(errorStatus.getHttpStatus(), exception.getMessage());
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorResponse: {" + System.lineSeparator() +
                "Code: " + httpStatus.getCode() + "," + System.lineSeparator() +
                "Message: " + message + System.lineSeparator() +
                "}";
    }
}
