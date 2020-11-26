package webserver.request;

public enum Status {
    OK("200 OK"),
    REDIRECT("302 FOUND"),
    NOT_FOUND("404 Not Found"),
    INTERNAL_SERVER_ERROR("500 Internal Server Error");

    String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
