package webserver;

import webserver.constant.HttpStatus;

public class HttpException extends Exception {

    private HttpStatus httpStatus;
    private String message;

    public HttpException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static HttpException notImplemented(String message) {
        return new HttpException(HttpStatus.NOT_IMPLEMENTED, message);
    }

    public static HttpException internalServerError(String message) {
        return new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public HttpResponse toHttpResponse() {
        return HttpResponse.Builder.prepare()
                .status(httpStatus)
                .body(message.getBytes())
                .build();
    }

}
