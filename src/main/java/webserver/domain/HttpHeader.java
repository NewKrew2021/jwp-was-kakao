package webserver.domain;

public enum HttpHeader {
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie");

    private final String message;

    HttpHeader(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
