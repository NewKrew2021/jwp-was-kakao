package webserver.http;

public enum HttpCode {
    // https://developer.mozilla.org/ko/docs/Web/HTTP/Status
    _200("200", "OK"),
    _302("302", "Found"),
    _404("404", "Not Found"),
    _405("405", "Method Not Allowed"),
    _500("500", "Internal Server Error");

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
