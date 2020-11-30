package webserver;

public enum ResponseStatus {
    OK("200 OK"),
    SEE_OTHER("302 Found"),
    NOT_FOUND("404 Not Found"),
    INTERNAL_SERVER_ERROR("500 Internal Server Error");

    private final String statusValue;

    ResponseStatus(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getStatusValue() {
        return statusValue;
    }
}
