package webserver.http;

public enum HttpCode {
    _200_OK("200", "OK");

    private final String statusCode;
    private final String message;

    HttpCode(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
