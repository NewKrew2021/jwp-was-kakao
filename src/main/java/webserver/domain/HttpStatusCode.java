package webserver.domain;

public enum HttpStatusCode {
    OK(200, "OK"),
    FOUND(302, "Found", new HttpHeader[]{HttpHeader.LOCATION}),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    private final int code;
    private final String message;
    private final HttpHeader[] requiredHeaders;

    HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
        this.requiredHeaders = new HttpHeader[]{};
    }

    HttpStatusCode(int code, String message, HttpHeader[] requiredHeaders) {
        this.code = code;
        this.message = message;
        this.requiredHeaders = requiredHeaders;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpHeader[] getRequiredHeaders() {
        return requiredHeaders;
    }
}
