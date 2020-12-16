package webserver.constant;

public enum HttpStatus {

    OK(200, "OK"),

    NOT_FOUND(404, "Not Found"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented")
    ;

    private final int statusCode;
    private final String reasonPhrase;

    HttpStatus(int status, String reason) {
        this.statusCode = status;
        this.reasonPhrase = reason;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

}
