package framework.common;

public enum HttpHeaders {

    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie");

    private String header;

    HttpHeaders(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
