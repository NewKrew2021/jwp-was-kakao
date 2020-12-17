package webserver;

import utils.Utils;
import webserver.constant.HttpStatus;

public class HttpException extends Exception {

    private HttpStatus httpStatus;
    private String message;

    public HttpException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = Utils.defaultIfNull(message);
    }

    public HttpException(HttpStatus httpStatus) {
        this(httpStatus, null);
    }

    public static HttpException notImplemented(String message) {
        return new HttpException(HttpStatus.NOT_IMPLEMENTED, message);
    }

    public static HttpException internalServerError(String message) {
        return new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static HttpException notFound() {
        return new HttpException(HttpStatus.NOT_FOUND, null);
    }

    public HttpResponse toHttpResponse() {
        return new HttpResponse(httpStatus).setBody(message.getBytes(), true);
    }

}
